package com.example.munch.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.munch.R
import com.example.munch.databinding.FragmentAdminHistoryBinding
import com.example.munch.databinding.FragmentAdminProviderBinding
import java.util.Calendar

class AdminHistoryFragment : Fragment() {
  var _binding: FragmentAdminHistoryBinding? = null
  val binding get() = _binding!!

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?,
  ): View? {
    _binding = FragmentAdminHistoryBinding.inflate(inflater, container, false)
    binding.etDateMin.setOnClickListener {
      val c = Calendar.getInstance()
      val year = c.get(Calendar.YEAR)
      val month = c.get(Calendar.MONTH)
      val day = c.get(Calendar.DAY_OF_MONTH)
      val datePickerDialog = DatePickerDialog(
        requireContext(),
        { view, year , monthOfYear, dayOfMonth ->
          val dat = (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
          binding.etDateMin.setText(dat)
        },
        year,
        month,
        day
      )
      datePickerDialog.show()
    }
    binding.etDateMax.setOnClickListener {
      val c = Calendar.getInstance()
      val year = c.get(Calendar.YEAR)
      val month = c.get(Calendar.MONTH)
      val day = c.get(Calendar.DAY_OF_MONTH)
      val datePickerDialog = DatePickerDialog(
        requireContext(),
        { view, year , monthOfYear, dayOfMonth ->
          val dat = (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
          binding.etDateMax.setText(dat)
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