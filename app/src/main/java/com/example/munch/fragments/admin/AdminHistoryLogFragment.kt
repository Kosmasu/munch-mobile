package com.example.munch.fragments.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.munch.adapter.AdminHistoryLogAdapter
import com.example.munch.api.Retrofit
import com.example.munch.api.history.HistoryStore
import com.example.munch.databinding.FragmentAdminHistoryLogBinding
import com.example.munch.helpers.FragmentUtils.isSafeFragment
import com.example.munch.model.HistoryLog
import kotlinx.coroutines.launch

class AdminHistoryLogFragment(date_lower: String = "", date_upper: String = "") : Fragment() {
    private var _binding: FragmentAdminHistoryLogBinding? = null
    val binding get() = _binding!!

    var reqMap : Map<String, String> = mapOf("date_lower" to date_lower, "date_upper" to date_upper)
    var listHistoryLog : List<HistoryLog> = listOf()
    lateinit var historyStore : HistoryStore
    lateinit var logAdapter : AdminHistoryLogAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdminHistoryLogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        historyStore = HistoryStore.getInstance(requireContext())
        Retrofit.coroutine.launch {
            try {
                listHistoryLog = historyStore.logUnpaginated(reqMap).body()?.data!!

                if (isSafeFragment()) {
                    requireActivity().runOnUiThread {
                        logAdapter = AdminHistoryLogAdapter(listHistoryLog)
                        binding.rvAdminHistoryLog.addItemDecoration(
                            DividerItemDecoration(
                                requireContext(),
                                DividerItemDecoration.VERTICAL
                            )
                        )
                        binding.rvAdminHistoryLog.adapter = logAdapter
                        binding.rvAdminHistoryLog.layoutManager =
                            LinearLayoutManager(requireContext())
                        logAdapter.notifyDataSetChanged()
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