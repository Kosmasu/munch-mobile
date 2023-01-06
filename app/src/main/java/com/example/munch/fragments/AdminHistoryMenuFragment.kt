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
import com.example.munch.adapter.AdminHistoryMenuAdapter
import com.example.munch.api.Retrofit
import com.example.munch.api.history.HistoryStore
import com.example.munch.databinding.FragmentAdminHistoryMenuBinding
import com.example.munch.model.HistoryMenu
import kotlinx.coroutines.launch

class AdminHistoryMenuFragment : Fragment() {
    var _binding: FragmentAdminHistoryMenuBinding? = null
    val binding get() = _binding!!

    var reqMap : Map<String, String> = mapOf("date_lower" to "", "date_upper" to "")
    var listHistoryMenu : List<HistoryMenu> = listOf()
    lateinit var historyStore : HistoryStore
    lateinit var menuAdapter : AdminHistoryMenuAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAdminHistoryMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        historyStore = HistoryStore.getInstance(requireContext())
        Retrofit.coroutine.launch {
            listHistoryMenu = historyStore.menuUnpaginated(reqMap).data

            (requireContext() as Activity).runOnUiThread {
                menuAdapter = AdminHistoryMenuAdapter(listHistoryMenu)
                binding.rvAdminHistoryMenu.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
                binding.rvAdminHistoryMenu.adapter = menuAdapter
                binding.rvAdminHistoryMenu.layoutManager = LinearLayoutManager(requireContext())
                menuAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}