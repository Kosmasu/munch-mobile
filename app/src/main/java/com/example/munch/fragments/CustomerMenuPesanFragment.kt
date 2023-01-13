package com.example.munch.fragments

import android.app.Activity
import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.munch.adapter.CustomerPesanAdapter
import com.example.munch.api.Retrofit
import com.example.munch.api.menu.MenuStore
import com.example.munch.databinding.FragmentCustomerMenuPesanBinding
import com.example.munch.helpers.FragmentUtils.isSafeFragment
import com.example.munch.model.Menu
import kotlinx.coroutines.launch
import java.util.*

class CustomerMenuPesanFragment(provider_id : ULong) : Fragment() {
    private var _binding: FragmentCustomerMenuPesanBinding? = null
    val binding get() = _binding!!

    val reqMap : Map<String, String> = mapOf("provider_id" to provider_id.toString(), "menu_status" to "tersedia", "sort_column" to "menu_nama", "sort_type" to "asc")
    lateinit var menuStore : MenuStore
    lateinit var menuAdapter : CustomerPesanAdapter
    var listMenu : List<Menu> = listOf()
    var orderMenu : ArrayList<Menu> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        menuStore = MenuStore.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCustomerMenuPesanBinding.inflate(inflater, container, false)
        binding.etDatePesanMenu.setOnClickListener {
            val calendar = Calendar.getInstance()
            val calendarYear = calendar.get(Calendar.YEAR)
            val calendarMonth = calendar.get(Calendar.MONTH)
            val calendarDay = calendar.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { view, year, monthOfYear, dayOfMonth ->
                    val dat = "$year-${monthOfYear + 1}-$dayOfMonth"
                    binding.etDatePesanMenu.setText(dat)
                },
                calendarYear,
                calendarMonth,
                calendarDay,
            )
            datePickerDialog.show()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Retrofit.coroutine.launch {
            try {
                listMenu = menuStore.fetchUnpaginated(reqMap).body()?.data!!
                Log.d("LIST MENU PROVIDER", listMenu.toString())

                if (isSafeFragment()) {
                    (context as Activity).runOnUiThread {
                        menuAdapter = CustomerPesanAdapter(listMenu)
                        binding.rvCheckboxMenu.adapter = menuAdapter
                        binding.rvCheckboxMenu.layoutManager = LinearLayoutManager(requireContext())
                        menuAdapter.notifyDataSetChanged()
                    }
                }
            } catch (e: Exception) {
                if (isSafeFragment()) {
                    (context as Activity).runOnUiThread {
                        Toast.makeText(requireContext(), "Error fetching menu", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        binding.btnPesanTambah.setOnClickListener {
            Toast.makeText(requireContext(), "Menu ditambahkan!", Toast.LENGTH_SHORT).show()
        }

        binding.btnToSummary.setOnClickListener {
            Toast.makeText(requireContext(), "$orderMenu", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}