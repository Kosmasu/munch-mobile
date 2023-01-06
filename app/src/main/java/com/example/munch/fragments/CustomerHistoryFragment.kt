package com.example.munch.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.example.munch.R
import com.example.munch.databinding.FragmentCustomerHistoryBinding
import com.example.munch.databinding.FragmentCustomerProfileBinding
import java.util.*

class CustomerHistoryFragment : Fragment() {
    private var _binding: FragmentCustomerHistoryBinding? = null
    val binding get() = _binding!!

    var filter : String = "pemesanan"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCustomerHistoryBinding.inflate(inflater, container, false)
        binding.etDateMinCustomer.setOnClickListener {
            val calendar = Calendar.getInstance()
            val calendarYear = calendar.get(Calendar.YEAR)
            val calendarMonth = calendar.get(Calendar.MONTH)
            val calendarDay = calendar.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { view, year, monthOfYear, dayOfMonth ->
                    val dat = "$year-${monthOfYear + 1}-$dayOfMonth"
                    binding.etDateMinCustomer.setText(dat)
                    filter()
                },
                calendarYear,
                calendarMonth,
                calendarDay,
            )
            datePickerDialog.show()
        }
        binding.etDateMaxCustomer.setOnClickListener {
            val calendar = Calendar.getInstance()
            val calendarYear = calendar.get(Calendar.YEAR)
            val calendarMonth = calendar.get(Calendar.MONTH)
            val calendarDay = calendar.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { view, year, monthOfYear, dayOfMonth ->
                    val dat = "$year-${monthOfYear + 1}-$dayOfMonth"
                    binding.etDateMaxCustomer.setText(dat)
                    filter()
                },
                calendarYear,
                calendarMonth,
                calendarDay,
            )
            datePickerDialog.show()
        }
        binding.spinnerHistoryCustomerFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> {
                        filter = "pemesanan"
                    }
                    1 -> {
                        filter = "topup"
                    }
                }
                filter()
            }
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun filter() {
        childFragmentManager.beginTransaction().apply {
            when (filter) {
                "pemesanan" -> {
                    println("CUSTOMER PEMESANAN")
                    replace(binding.flFragmentCustomerHistory.id, CustomerHistoryPemesananFragment(binding.etDateMinCustomer.text.toString(), binding.etDateMaxCustomer.text.toString()), "CustomerHistoryPemesananFragment")
                }
                "topup" -> {
                    println("CUSTOMER TOPUP")
                    replace(binding.flFragmentCustomerHistory.id, CustomerHistoryTopupFragment(binding.etDateMinCustomer.text.toString(), binding.etDateMaxCustomer.text.toString()), "CustomerHistoryTopupFragment")
                }
            }
            setReorderingAllowed(true)
            commit()
        }
    }
}