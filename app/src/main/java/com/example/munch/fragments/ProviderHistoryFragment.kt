package com.example.munch.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.munch.R
import com.example.munch.databinding.FragmentDetailUserBinding
import com.example.munch.databinding.FragmentProviderHistoryBinding
import com.example.munch.databinding.FragmentProviderHomeBinding
import java.util.*


class ProviderHistoryFragment : Fragment() {
    var _binding: FragmentProviderHistoryBinding? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProviderHistoryBinding.inflate(inflater, container, false)
        binding.etDateMinPemesanan.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { view, year , monthOfYear, dayOfMonth ->
                    val dat = (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                    binding.etDateMinPemesanan.setText(dat)
                },
                year,
                month,
                day
            )
            datePickerDialog.show()
        }
        binding.etDateMaxPemesanan.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { view, year , monthOfYear, dayOfMonth ->
                    val dat = (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                    binding.etDateMaxPemesanan.setText(dat)
                },
                year,
                month,
                day
            )
            datePickerDialog.show()
        }
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}