package com.example.munch.fragments.customer

import android.app.Activity
import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.munch.activities.CustomerHomeActivity
import com.example.munch.adapter.CustomerPesanAdapter
import com.example.munch.adapter.CustomerSummaryAdapter
import com.example.munch.api.Retrofit
import com.example.munch.api.menu.MenuStore
import com.example.munch.api.pesanan.PesananStore
import com.example.munch.api.pesanan.RequestBodyDetailMenu
import com.example.munch.api.pesanan.RequestBodyStore
import com.example.munch.databinding.FragmentCustomerMenuPesanBinding
import com.example.munch.helpers.FragmentUtils.isSafeFragment
import com.example.munch.model.Menu
import kotlinx.coroutines.launch
import java.util.*

class CustomerMenuPesanFragment(val provider_id : ULong) : Fragment() {
    private var _binding: FragmentCustomerMenuPesanBinding? = null
    val binding get() = _binding!!

    val reqMap : Map<String, String> = mapOf("provider_id" to provider_id.toString(), "menu_status" to "tersedia", "sort_column" to "menu_nama", "sort_type" to "asc")
    lateinit var pesananStore : PesananStore
    lateinit var menuStore : MenuStore
    lateinit var menuAdapter : CustomerPesanAdapter
    lateinit var summaryAdapter: CustomerSummaryAdapter
    var listMenu : List<Menu> = listOf()
    var orderMenu : ArrayList<ULong> = arrayListOf()
    var summary : ArrayList<RequestBodyDetailMenu> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pesananStore = PesananStore.getInstance(requireContext())
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

                if (isSafeFragment()) {
                    (context as Activity).runOnUiThread {
                        menuAdapter = CustomerPesanAdapter(listMenu)
                        binding.rvCheckboxMenu.adapter = menuAdapter
                        binding.rvCheckboxMenu.layoutManager = LinearLayoutManager(requireContext())
                        menuAdapter.notifyDataSetChanged()

                        menuAdapter.onClickListener = fun (menu: Menu) {
                            if (orderMenu.contains(menu.menu_id)) {
                                orderMenu.remove(menu.menu_id)
                            } else {
                                orderMenu.add(menu.menu_id)
                            }
                            Toast.makeText(requireContext(), "$orderMenu", Toast.LENGTH_SHORT).show()
                        }
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

        // summary
        summaryAdapter = CustomerSummaryAdapter(requireActivity(),summary)
        binding.rvGridPemesanan.adapter = summaryAdapter
        binding.rvGridPemesanan.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)

        binding.btnPesanTambah.setOnClickListener {
            if (orderMenu.isNotEmpty() && binding.etDatePesanMenu.text.isNotBlank()) {
                orderMenu.forEach {
                    summary.add(RequestBodyDetailMenu(it, binding.etDatePesanMenu.text.toString()))
                }
                orderMenu.clear()
                Toast.makeText(requireContext(), "$summary", Toast.LENGTH_SHORT).show()
                println(summary)
                summaryAdapter.notifyItemInserted(summary.lastIndex)
            }
            calcSummaryStat()
        }

        binding.btnProceedToCart.setOnClickListener {
            if (summary.isNotEmpty()) {
                Retrofit.coroutine.launch {
                    try {
                        val response = pesananStore.store(RequestBodyStore(provider_id, summary))

                        if (isSafeFragment()) {
                            (context as Activity).runOnUiThread {
                                if (response.code() == 200) {
                                    Toast.makeText(requireContext(), "Berhasil store menu", Toast.LENGTH_SHORT).show()
                                    (activity as CustomerHomeActivity).toCart()
                                } else {
                                    Toast.makeText(requireContext(), "Error store menu", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    } catch (e: Exception) {
                        if (isSafeFragment()) {
                            (context as Activity).runOnUiThread {
                                Toast.makeText(requireContext(), "Error store menu", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Tidak ada yang dipesan!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun calcSummaryStat() {
        var jumlah =0
        var total = 0
        for (item in summary) {
            jumlah += item.detail_jumlah

            var menu : Menu
            Retrofit.coroutine.launch {
                try {
                    val resp = menuStore.fetch(item.menu_id)
                    menu = resp.body()?.data!!
                } catch (e: Exception) {
                    Log.e("CustomerMenuPesanFragment", "onViewCreated: API Server error", e)
                    requireActivity().runOnUiThread {
                        Toast.makeText(context, "Server error", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}