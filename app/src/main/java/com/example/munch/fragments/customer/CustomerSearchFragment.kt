package com.example.munch.fragments.customer

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.munch.activities.CustomerHomeActivity
import com.example.munch.adapter.CustomerSearchAdapter
import com.example.munch.api.Retrofit
import com.example.munch.api.menu.MenuStore
import com.example.munch.databinding.FragmentCustomerSearchBinding
import com.example.munch.helpers.FragmentUtils.isSafeFragment
import com.example.munch.model.Menu
import kotlinx.coroutines.launch

class CustomerSearchFragment : Fragment() {
    private var _binding: FragmentCustomerSearchBinding? = null
    val binding get() = _binding!!

    var column = "menu_nama"
    var type = "asc"

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
            try {
                filter()

                if (isSafeFragment()) {
                    (context as Activity).runOnUiThread {
                        menuAdapter = CustomerSearchAdapter(listMenu)
                        binding.rvCustomerSearch.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
                        binding.rvCustomerSearch.adapter = menuAdapter
                        binding.rvCustomerSearch.layoutManager = LinearLayoutManager(requireContext())
                        menuAdapter.notifyDataSetChanged()

                        menuAdapter.onClickListener = fun (menu: Menu) {
                            (activity as CustomerHomeActivity).toProvider(menu.users_id)
                        }
                    }
                }
            } catch (e: Exception) {
                if (isSafeFragment()) {
                    (context as Activity).runOnUiThread {
                        Toast.makeText(requireContext(), "Error fetching menu", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }

        binding.btnCustomerDoSearch.setOnClickListener {
            Retrofit.coroutine.launch {
                filter()
            }
        }

        binding.spinnerSearchFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> {
                        column = "menu_nama"
                    }
                    1 -> {
                        column = "menu_harga"
                    }
                }
                filter()
            }
        }

        binding.spinnerSearchSort.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> {
                        type = "asc"
                    }
                    1 -> {
                        type = "desc"
                    }
                }
                filter()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun filter() {
        val reqMap : Map<String, String> = mapOf("menu_nama" to binding.etCustomerSearch.text.toString(), "menu_status" to "tersedia", "sort_column" to column, "sort_type" to type)
        Retrofit.coroutine.launch {
            try {
                listMenu = menuStore.fetchUnpaginated(reqMap).body()?.data!!

                if (isSafeFragment()) {
                    (context as Activity).runOnUiThread {
                        menuAdapter.data = listMenu
                        menuAdapter.notifyDataSetChanged()
                    }
                }
            } catch (e: Exception) {
                if (isSafeFragment()) {
                    (context as Activity).runOnUiThread {
                        Toast.makeText(
                            requireContext(),
                            "Error fetching menu",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}