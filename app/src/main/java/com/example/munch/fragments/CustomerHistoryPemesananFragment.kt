package com.example.munch.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.munch.R
import com.example.munch.adapter.CustomerHistoryPemesananAdapter
import com.example.munch.api.Retrofit
import com.example.munch.api.pesanan.PesananStore
import com.example.munch.databinding.FragmentCustomerHistoryPemesananBinding
import com.example.munch.databinding.FragmentDetailMenuBinding
import com.example.munch.model.HistoryPemesanan
import kotlinx.coroutines.launch

class CustomerHistoryPemesananFragment(date_lower: String = "", date_upper: String = "") : Fragment() {
    private var _binding: FragmentCustomerHistoryPemesananBinding? = null
    val binding get() = _binding!!

    var reqMap : Map<String, String> = mapOf("date_lower" to date_lower, "date_upper" to date_upper)
    lateinit var pesananStore : PesananStore
    lateinit var pemesananAdapter : CustomerHistoryPemesananAdapter
    var listHistoryPemesanan : List<HistoryPemesanan> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCustomerHistoryPemesananBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pesananStore = PesananStore.getInstance(requireContext())
        Retrofit.coroutine.launch {
            try {
                listHistoryPemesanan = pesananStore.fetchUnpaginated(reqMap).data

                requireActivity().runOnUiThread {
                    pemesananAdapter = CustomerHistoryPemesananAdapter(listHistoryPemesanan)
                    binding.rvCustomerHistoryPemesanan.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
                    binding.rvCustomerHistoryPemesanan.adapter = pemesananAdapter
                    binding.rvCustomerHistoryPemesanan.layoutManager = LinearLayoutManager(requireContext())
                    pemesananAdapter.notifyDataSetChanged()

                    pemesananAdapter.onClickListener = fun (it: View, position: Int, pemesanan: HistoryPemesanan) {
                        val popUp = PopupMenu(requireContext(), it)
                        popUp.menuInflater.inflate(R.menu.menu_popup_detailonly, popUp.menu)
                        popUp.setOnMenuItemClickListener {
                            return@setOnMenuItemClickListener when(it.itemId) {
                                R.id.menu_popup_detailonly -> {
                                    true
                                }
                                else -> {
                                    false
                                }
                            }
                        }
                        popUp.show()
                    }
                }
            } catch (e: Exception) {
                requireActivity().runOnUiThread {
                    Toast.makeText(requireContext(), "Check Date Again", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}