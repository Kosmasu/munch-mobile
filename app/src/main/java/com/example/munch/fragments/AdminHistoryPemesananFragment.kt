package com.example.munch.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.munch.activities.AdminHomeActivity
import com.example.munch.adapter.AdminHistoryPemesananAdapter
import com.example.munch.api.Retrofit
import com.example.munch.api.pesanan.PesananStore
import com.example.munch.databinding.FragmentAdminHistoryPemesananBinding
import com.example.munch.helpers.FragmentUtils.isSafeFragment
import com.example.munch.model.HistoryPemesanan
import kotlinx.coroutines.launch

class AdminHistoryPemesananFragment(date_lower: String = "", date_upper: String = "") : Fragment() {
    private var _binding: FragmentAdminHistoryPemesananBinding? = null
    val binding get() = _binding!!

    var reqMap : Map<String, String> = mapOf("date_lower" to date_lower, "date_upper" to date_upper)
    var listHistoryPemesanan : List<HistoryPemesanan> = listOf()
    lateinit var pesananStore : PesananStore
    lateinit var pemesananAdapter : AdminHistoryPemesananAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdminHistoryPemesananBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pesananStore = PesananStore.getInstance(requireContext())
        Retrofit.coroutine.launch {
            try {
                listHistoryPemesanan = pesananStore.fetchUnpaginated(reqMap).body()?.data!!

                if (isSafeFragment()) {
                    requireActivity().runOnUiThread {
                        pemesananAdapter = AdminHistoryPemesananAdapter(listHistoryPemesanan)
                        binding.rvAdminHistoryPemesanan.addItemDecoration(
                            DividerItemDecoration(
                                requireContext(),
                                DividerItemDecoration.VERTICAL
                            )
                        )
                        binding.rvAdminHistoryPemesanan.adapter = pemesananAdapter
                        binding.rvAdminHistoryPemesanan.layoutManager =
                            LinearLayoutManager(requireContext())
                        pemesananAdapter.notifyDataSetChanged()

                        pemesananAdapter.onClickListener = fun(pemesanan: HistoryPemesanan) {
                            (requireActivity() as AdminHomeActivity).toDetail(pemesanan.pemesanan_id)
                        }
                    }
                }
            } catch (e: Exception) {
                if (isSafeFragment()) {
                    requireActivity().runOnUiThread {
                        Toast.makeText(requireContext(), "Check Date Again", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}