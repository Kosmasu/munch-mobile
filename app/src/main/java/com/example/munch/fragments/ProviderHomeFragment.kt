package com.example.munch.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.munch.adapter.ProviderNewOrderAdapter
import com.example.munch.api.Retrofit
import com.example.munch.api.auth.AuthStore
import com.example.munch.api.auth.MyStatResponse
import com.example.munch.api.pesanan.PesananStore
import com.example.munch.databinding.FragmentProviderHomeBinding
import com.example.munch.helpers.CurrencyUtils.toRupiah
import kotlinx.coroutines.launch


class ProviderHomeFragment : Fragment() {
    private var TAG = "ProviderHomeFragment"
    private var _binding: FragmentProviderHomeBinding? = null
    val binding get() = _binding!!
    private lateinit var authStore : AuthStore
    lateinit var myStat : MyStatResponse
    private lateinit var pesananStore: PesananStore

    lateinit var providerNewOrderAdapter: ProviderNewOrderAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authStore = AuthStore.getInstance(requireContext())
        pesananStore = PesananStore.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProviderHomeBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: $binding")
        Retrofit.coroutine.launch {
            try {
                myStat = authStore.myStat().data!!

//                val params = mapOf("pemesanan_status" to "menunggu", "sort" to mapOf("column" to "created_at", "type" to "asc"))
                val params = mapOf("pemesanan_status" to "menunggu")
                Log.d(TAG, "onViewCreated: $params")
                val pesanan = pesananStore.fetchUnpaginated(params)
                print(pesanan)
            } catch (e: Exception){
                Log.e(TAG, "onViewCreated: API Server error", e)
                requireActivity().runOnUiThread {
                    Toast.makeText(requireContext(), "Server error", Toast.LENGTH_SHORT).show()
                }
            }

            if (_binding != null) {
                requireActivity().runOnUiThread {
                    binding.tvCurrentCustomer.text = myStat.thismonth_delivery.toString()
                    binding.tvDeliveries.text = myStat.made_delivery.toString()
                    binding.tvSales.text = myStat.total_pendapatan?.toRupiah() ?: "Rp. 0"
                }
            }
        }

//        providerNewOrderAdapter = ProviderNewOrderAdapter(requireActivity(),)
//        binding.rvNewOrders.adapter =
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