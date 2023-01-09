package com.example.munch.fragments

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.munch.R
import com.example.munch.adapter.CustomerSearchAdapter
import com.example.munch.api.Retrofit
import com.example.munch.api.menu.MenuStore
import com.example.munch.databinding.FragmentCustomerProfileBinding
import com.example.munch.databinding.FragmentCustomerSearchBinding
import com.example.munch.model.HistoryPemesanan
import com.example.munch.model.Menu
import kotlinx.coroutines.launch

class CustomerSearchFragment : Fragment() {
    private var _binding: FragmentCustomerSearchBinding? = null
    val binding get() = _binding!!

    var search = ""

    var reqMap : Map<String, String> = mapOf("menu_nama" to search, "menu_status" to "tersedia")
    lateinit var menuStore : MenuStore
    lateinit var menuAdapter : CustomerSearchAdapter
    var listMenu : List<Menu> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        menuStore = MenuStore.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCustomerSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Retrofit.coroutine.launch {
            listMenu = menuStore.fetchUnpaginated(reqMap).data

            (context as Activity).runOnUiThread {
                println(listMenu)
                menuAdapter = CustomerSearchAdapter(listMenu)
                binding.rvCustomerSearch.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
                binding.rvCustomerSearch.adapter = menuAdapter
                binding.rvCustomerSearch.layoutManager = LinearLayoutManager(requireContext())
                menuAdapter.notifyDataSetChanged()

                menuAdapter.onClickListener = fun (it: View, position: Int, menu: Menu) {
                    println(menu)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}