package com.example.munch.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.munch.adapter.CustomerHistoryTopupAdapter
import com.example.munch.api.Retrofit
import com.example.munch.api.history.HistoryStore
import com.example.munch.databinding.FragmentCustomerHistoryTopupBinding
import com.example.munch.model.HistoryTopUp
import kotlinx.coroutines.launch

class CustomerHistoryTopupFragment(date_lower: String = "", date_upper: String = "") : Fragment() {
    private var _binding: FragmentCustomerHistoryTopupBinding? = null
    val binding get() = _binding!!

    var reqMap : Map<String, String> = mapOf("date_lower" to date_lower, "date_upper" to date_upper)
    lateinit var historyStore : HistoryStore
    lateinit var topupAdapter : CustomerHistoryTopupAdapter
    var listHistoryTopup : List<HistoryTopUp> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        historyStore = HistoryStore.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCustomerHistoryTopupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Retrofit.coroutine.launch {
            try {
                listHistoryTopup = historyStore.topUpUnpaginated(reqMap).response.body()?.data!!

                requireActivity().runOnUiThread {
                    topupAdapter = CustomerHistoryTopupAdapter(listHistoryTopup)
                    binding.rvCustomerHistoryTopup.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
                    binding.rvCustomerHistoryTopup.adapter = topupAdapter
                    binding.rvCustomerHistoryTopup.layoutManager = LinearLayoutManager(requireContext())
                    topupAdapter.notifyDataSetChanged()
                }
            } catch (e: Exception) {
                requireActivity().runOnUiThread {
                    Toast.makeText(requireContext(), "Error fetch topup", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}