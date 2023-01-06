package com.example.munch.fragments

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
          val dat = (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
          binding.etDateMin.setText(dat)
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
          val dat = (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
          binding.etDateMax.setText(dat)
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
            println("ADMIN LOG")
            childFragmentManager.beginTransaction().apply {
              replace(binding.flFragmentAdminHistory.id, AdminHistoryLogFragment(binding.etDateMin.toString(), binding.etDateMax.toString()), "AdminHistoryFragment")
              setReorderingAllowed(true)
              commit()
            }
          }
          1 -> {
            println("ADMIN MENU")
            childFragmentManager.beginTransaction().apply {
              replace(binding.flFragmentAdminHistory.id, AdminHistoryMenuFragment(), "AdminHistoryFragment")
              setReorderingAllowed(true)
              commit()
            }
          }
          2 -> {
            println("ADMIN PEMESANAN")
            childFragmentManager.beginTransaction().apply {
              replace(binding.flFragmentAdminHistory.id, AdminHistoryPemesananFragment(), "AdminHistoryFragment")
              setReorderingAllowed(true)
              commit()
            }
          }
          3 -> {
            println("ADMIN TOPUP")
            childFragmentManager.beginTransaction().apply {
              replace(binding.flFragmentAdminHistory.id, AdminHistoryTopupFragment(), "AdminHistoryFragment")
              setReorderingAllowed(true)
              commit()
            }
          }
        }
      }
    }
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    // FOR SOME REASON, THROWS ERROR WHEN UNCOMMENTED - Kevin
//    childFragmentManager.beginTransaction().apply {
//      replace(binding.flFragmentAdminHistory.id, AdminHistoryLogFragment(), "AdminHistoryFragment")
//      setReorderingAllowed(true)
//      commit()
//    }
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}