package com.example.munch.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.munch.R
import com.example.munch.adapter.ProviderDeliveryAdapter
import com.example.munch.adapter.ProviderNewOrderAdapter
import com.example.munch.api.Retrofit
import com.example.munch.api.auth.AuthStore
import com.example.munch.api.auth.MyStatResponse
import com.example.munch.api.pesanan.PesananStore
import com.example.munch.databinding.FragmentProviderHomeBinding
import com.example.munch.helpers.CurrencyUtils.toRupiah
import com.example.munch.model.DetailPemesanan
import com.example.munch.model.HistoryPemesanan
import kotlinx.coroutines.launch
import java.time.LocalDate


class ProviderHomeFragment : Fragment() {
    private var TAG = "ProviderHomeFragment"
    private var _binding: FragmentProviderHomeBinding? = null
    val binding get() = _binding!!
    private lateinit var authStore : AuthStore
    private lateinit var myStat : MyStatResponse
    private lateinit var pesananStore: PesananStore

    private lateinit var providerNewOrderAdapter: ProviderNewOrderAdapter
    private lateinit var providerDeliveryAdapter: ProviderDeliveryAdapter
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

        providerNewOrderAdapter = ProviderNewOrderAdapter(requireActivity(), arrayListOf())
        providerNewOrderAdapter.onClickListener { newOrderView, position, pemesanan ->
            val popUp = PopupMenu(requireContext(), newOrderView)
            popUp.menuInflater.inflate(R.menu.menu_popup_orders, popUp.menu)
            popUp.setOnMenuItemClickListener {
                return@setOnMenuItemClickListener when(it.itemId) {
                    R.id.menu_order_detail -> {
                        parentFragmentManager.beginTransaction().apply {
                            replace(R.id.flFragmentProvider, DetailPemesananFragment.newInstance(pemesanan.pemesanan_id) , tag)
                            setReorderingAllowed(true)
                            addToBackStack(tag)
                            commit()
                        }
                        true
                    }
                    R.id.menu_order_terima -> {
                        Retrofit.coroutine.launch {
                            try {
                                pesananStore.accept(pemesanan.pemesanan_id)
                                requireActivity().runOnUiThread{
                                    Toast.makeText(
                                        context,
                                        "Successfully accepted pesanan",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    providerNewOrderAdapter.newOrder.removeAt(position)
                                    providerNewOrderAdapter.notifyItemRemoved(position)
                                    updateView()
                                }
                            } catch (e: Exception) {
                                Log.e(TAG, "onViewCreated: API Server error", e)
                                requireActivity().runOnUiThread {
                                    Toast.makeText(requireContext(), "Server error", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                        true
                    }
                    R.id.menu_order_tolak -> {
                        Retrofit.coroutine.launch {
                            try {
                                pesananStore.reject(pemesanan.pemesanan_id)
                                requireActivity().runOnUiThread{
                                    Toast.makeText(
                                        context,
                                        "Successfully rejected pesanan",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                updateView()
                            } catch (e: Exception) {
                                Log.e(TAG, "onViewCreated: API Server error", e)
                                requireActivity().runOnUiThread {
                                    Toast.makeText(requireContext(), "Server error", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                        true
                    }
                    else -> {
                        false
                    }
                }
            }
            popUp.show()
        }
        binding.rvNewOrders.adapter = providerNewOrderAdapter
        binding.rvNewOrders.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        providerDeliveryAdapter = ProviderDeliveryAdapter(requireContext(), arrayListOf())
        providerDeliveryAdapter.onClickListener { popupView, position, pemesanan ->
            val popUp = PopupMenu(requireContext(), popupView)
            popUp.menuInflater.inflate(R.menu.menu_popup_delivery, popUp.menu)
            popUp.setOnMenuItemClickListener{
                return@setOnMenuItemClickListener when(it.itemId) {
                    R.id.menu_delivery_detail -> {
                        // TODO detail delivery
                        true
                    }
                    R.id.menu_delivery_kirim -> {
                        Retrofit.coroutine.launch {
                            try {
                                pesananStore.deliver(pemesanan.pemesanan_id)
                                requireActivity().runOnUiThread{
                                    Toast.makeText(
                                        context,
                                        "Successfully delivered pesanan",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                updateView()
                            } catch (e: Exception) {
                                Log.e(TAG, "onViewCreated: API Server error", e)
                                requireActivity().runOnUiThread {
                                    Toast.makeText(requireContext(), "Server error", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                        true
                    }
                    else -> {
                        false
                    }
                }
            }
            popUp.show()
        }
        binding.rvDeliveries.adapter = providerDeliveryAdapter
        binding.rvDeliveries.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)


        updateView()
    }

    private fun updateView() {
        var newPesanan = listOf<HistoryPemesanan>()
        var pesananAktif = listOf<DetailPemesanan>()

        Retrofit.coroutine.launch {
            try {
                myStat = authStore.myStat().body()?.data!!

//                val params = mapOf("pemesanan_status" to "menunggu", "sort" to mapOf("column" to "created_at", "type" to "asc"))
                val paramNewOrder = mapOf("pemesanan_status" to "menunggu")
                Log.d(TAG, "onViewCreated: $paramNewOrder")

                newPesanan = pesananStore.fetchUnpaginated(paramNewOrder).body()?.data!!
                Log.d(TAG, "onViewCreated: newOrder= $newPesanan")


                val date = LocalDate.now()
                val paramPesananAktif = mapOf("month" to date.monthValue.toString(), "year" to date.year.toString())
                Log.d(TAG, "onViewCreated: $paramPesananAktif")

                pesananAktif = pesananStore.fetchDelivery(paramPesananAktif).body()?.data!!
                Log.d(TAG, "onViewCreated: delivery= $pesananAktif")


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

//                    if (pesanan.isEmpty()) {
//                        Toast.makeText(context, "No menu available", Toast.LENGTH_SHORT).show()
//                    } else {
                    providerNewOrderAdapter.newOrder.clear()
                    providerNewOrderAdapter.newOrder.addAll(newPesanan)
                    providerNewOrderAdapter.notifyDataSetChanged()
//                    providerNewOrderAdapter.notifyItemRangeChanged(0,newPesanan.size)
//                    }

                    providerDeliveryAdapter.pesananList.clear()
                    providerDeliveryAdapter.pesananList.addAll(pesananAktif)
                    providerDeliveryAdapter.notifyDataSetChanged()
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
            ProviderHomeFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}