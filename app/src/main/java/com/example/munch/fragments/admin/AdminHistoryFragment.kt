package com.example.munch.fragments.admin

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.example.munch.databinding.FragmentAdminHistoryBinding
import java.util.Calendar

class AdminHistoryFragment : Fragment() {
  private var _binding: FragmentAdminHistoryBinding? = null
  val binding get() = _binding!!

  var filter : String = "log"

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?,
  ): View {
    _binding = FragmentAdminHistoryBinding.inflate(inflater, container, false)
    binding.etDateMin.setOnClickListener {
      val calendar = Calendar.getInstance()
      val calendarYear = calendar.get(Calendar.YEAR)
      val calendarMonth = calendar.get(Calendar.MONTH)
      val calendarDay = calendar.get(Calendar.DAY_OF_MONTH)
      val datePickerDialog = DatePickerDialog(
        requireContext(),
        { view, year , monthOfYear, dayOfMonth ->
          val dat = "$year-${monthOfYear + 1}-$dayOfMonth"
          binding.etDateMin.setText(dat)
          filter()
        },
        calendarYear,
        calendarMonth,
        calendarDay
      )
      datePickerDialog.show()
    }
    binding.etDateMax.setOnClickListener {
      val calendar = Calendar.getInstance()
      val calendarYear = calendar.get(Calendar.YEAR)
      val calendarMonth = calendar.get(Calendar.MONTH)
      val calendarDay = calendar.get(Calendar.DAY_OF_MONTH)
      val datePickerDialog = DatePickerDialog(
        requireContext(),
        { view, year , monthOfYear, dayOfMonth ->
          val dat = "$year-${monthOfYear + 1}-$dayOfMonth"
          binding.etDateMax.setText(dat)
          filter()
        },
        calendarYear,
        calendarMonth,
        calendarDay
      )
      datePickerDialog.show()
    }
    binding.spinnerHistoryFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
      override fun onNothingSelected(parent: AdapterView<*>?) {}
      override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (position) {
          0 -> {
            filter = "log"
          }
          1 -> {
            filter = "menu"
          }
          2 -> {
            filter = "pemesanan"
          }
          3 -> {
            filter = "topup"
          }
        }
        filter()
      }
    }
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }

  fun filter() {
    childFragmentManager.beginTransaction().apply {
      when (filter) {
          "log" -> {
            println("ADMIN LOG")
            replace(binding.flFragmentAdminHistory.id, AdminHistoryLogFragment(binding.etDateMin.text.toString(), binding.etDateMax.text.toString()), "AdminHistoryLogFragment")
          }
          "menu" -> {
            println("ADMIN MENU")
            replace(binding.flFragmentAdminHistory.id, AdminHistoryMenuFragment(binding.etDateMin.text.toString(), binding.etDateMax.text.toString()), "AdminHistoryMenuFragment")
          }
          "pemesanan" -> {
            println("ADMIN PEMESANAN")
            replace(binding.flFragmentAdminHistory.id, AdminHistoryPemesananFragment(binding.etDateMin.text.toString(), binding.etDateMax.text.toString()), "AdminHistoryPemesananFragment")
          }
          "topup" -> {
            println("ADMIN TOPUP")
            replace(binding.flFragmentAdminHistory.id, AdminHistoryTopupFragment(binding.etDateMin.text.toString(), binding.etDateMax.text.toString()), "AdminHistoryTopupFragment")
          }
      }
      setReorderingAllowed(true)
      commit()
    }
  }
}