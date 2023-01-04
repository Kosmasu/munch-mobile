package com.example.munch.fragments

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.munch.adapter.AdminHistoryLogAdapter
import com.example.munch.adapter.AdminHistoryPemesananAdapter
import com.example.munch.api.Retrofit
import com.example.munch.api.pesanan.PesananStore
import com.example.munch.databinding.FragmentAdminHistoryPemesananBinding
import com.example.munch.model.HistoryPemesanan
import kotlinx.coroutines.launch

class AdminHistoryPemesananFragment : Fragment() {
    var _binding: FragmentAdminHistoryPemesananBinding? = null
    val binding get() = _binding!!

    var reqMap : Map<String, String> = mapOf("date_lower" to "", "date_upper" to "")
    var listHistoryPemesanan : List<HistoryPemesanan> = listOf()
    lateinit var pesananStore : PesananStore
    lateinit var pemesananAdapter : AdminHistoryPemesananAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAdminHistoryPemesananBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pesananStore = PesananStore.getInstance(requireContext())
        Retrofit.coroutine.launch {
            listHistoryPemesanan = pesananStore.fetchUnpaginated(reqMap).data

            (requireContext() as Activity).runOnUiThread {
                pemesananAdapter = AdminHistoryPemesananAdapter(listHistoryPemesanan)
                binding.rvAdminHistoryPemesanan.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
                binding.rvAdminHistoryPemesanan.adapter = pemesananAdapter
                binding.rvAdminHistoryPemesanan.layoutManager = LinearLayoutManager(requireContext())
                pemesananAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}