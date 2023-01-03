package com.example.munch.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.munch.api.Retrofit
import com.example.munch.api.auth.AuthStore
import com.example.munch.api.auth.MyStatResponse
import com.example.munch.databinding.FragmentProviderHomeBinding
import com.example.munch.helpers.CurrencyUtils.toRupiah
import kotlinx.coroutines.launch


class ProviderHomeFragment : Fragment() {
    private var _binding: FragmentProviderHomeBinding? = null
    val binding get() = _binding!!
    private val authStore = AuthStore.getInstance(requireContext())
    lateinit var myStat : MyStatResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProviderHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Retrofit.coroutine.launch {
            try {
                myStat = authStore.myStat().data!!
            } catch (e: Exception){
                requireActivity().runOnUiThread {
                    Toast.makeText(requireContext(), "Server error", Toast.LENGTH_SHORT).show()
                }
            }

            requireActivity().runOnUiThread {
                binding.tvCurrentCustomer.text = myStat.thismonth_delivery.toString()
                binding.tvDeliveries.text = myStat.made_delivery.toString()
                binding.tvSales.text = myStat.total_pendapatan?.toRupiah() ?: "Rp. 0"
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
            ProviderHomeFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}