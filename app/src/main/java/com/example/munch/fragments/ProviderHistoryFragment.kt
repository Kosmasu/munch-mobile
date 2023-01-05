package com.example.munch.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.munch.api.Retrofit
import com.example.munch.api.history.HistoryStore
import com.example.munch.databinding.FragmentProviderHistoryBinding
import kotlinx.coroutines.launch
import java.util.*


class ProviderHistoryFragment : Fragment() {
    private val TAG = "ProviderHistoryFragment"
    private var _binding: FragmentProviderHistoryBinding? = null
    val binding get() = _binding!!
    private lateinit var historyStore: HistoryStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        historyStore = HistoryStore.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProviderHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.etDateMinPemesanan.setOnClickListener {
            val calendar = Calendar.getInstance()
            val calendarYear = calendar.get(Calendar.YEAR)
            val calendarMonth = calendar.get(Calendar.MONTH)
            val calendarDay = calendar.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { view, year , monthOfYear, dayOfMonth ->
                    val dat = (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                    binding.etDateMinPemesanan.setText(dat)
                    updateHistory()
                },
                calendarYear,
                calendarMonth,
                calendarDay
            )
            datePickerDialog.show()
        }
        binding.etDateMaxPemesanan.setOnClickListener {
            val calendar = Calendar.getInstance()
            val calendarYear = calendar.get(Calendar.YEAR)
            val calendarMonth = calendar.get(Calendar.MONTH)
            val calendarDay = calendar.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { view, year , monthOfYear, dayOfMonth ->
                    val dat = (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                    binding.etDateMaxPemesanan.setText(dat)
                    updateHistory()
                },
                calendarYear,
                calendarMonth,
                calendarDay
            )
            datePickerDialog.show()
        }

        updateHistory()
    }

    fun updateHistory() {
        val dateStart = binding.etDateMinPemesanan
        val dateEnd = binding.etDateMaxPemesanan
        Retrofit.coroutine.launch {
            val params : MutableMap<String,String> = mutableMapOf()
            if (dateStart.text.toString() != ""){
                params["date_lower"] = dateStart.toString()
            }
            if (dateEnd.text.toString() != ""){
                params["date_upper"] = dateEnd.toString()
            }

            try {
                val historyMenu = historyStore.menuUnpaginated(params).data
                print(historyMenu)

            } catch (e: Exception) {
                Log.e(TAG, "updateHistory: API Server Error", e)
                requireActivity().runOnUiThread {
                    Toast.makeText(requireContext(), "Server error", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment ProfileFragment.
         */
        @JvmStatic
        fun newInstance() =
            ProviderHistoryFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}