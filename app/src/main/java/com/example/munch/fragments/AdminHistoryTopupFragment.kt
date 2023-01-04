package com.example.munch.fragments

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.munch.adapter.AdminHistoryTopupAdapter
import com.example.munch.api.Retrofit
import com.example.munch.api.history.HistoryStore
import com.example.munch.databinding.FragmentAdminHistoryTopupBinding
import com.example.munch.model.HistoryTopUp
import kotlinx.coroutines.launch

class AdminHistoryTopupFragment : Fragment() {
    var _binding: FragmentAdminHistoryTopupBinding? = null
    val binding get() = _binding!!

    var reqMap : Map<String, String> = mapOf("date_lower" to "", "date_upper" to "")
    var listHistoryTopUp : List<HistoryTopUp> = listOf()
    lateinit var historyStore : HistoryStore
    lateinit var topupAdapter : AdminHistoryTopupAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAdminHistoryTopupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        historyStore = HistoryStore.getInstance(requireContext())
        Retrofit.coroutine.launch {
            listHistoryTopUp = historyStore.topUpUnpaginated(reqMap).data

            (requireContext() as Activity).runOnUiThread {
                topupAdapter = AdminHistoryTopupAdapter(listHistoryTopUp)
                binding.rvAdminHistoryTopup.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
                binding.rvAdminHistoryTopup.adapter = topupAdapter
                binding.rvAdminHistoryTopup.layoutManager = LinearLayoutManager(requireContext())
                topupAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}