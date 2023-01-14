package com.example.munch.fragments

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.munch.R
import com.example.munch.activities.CustomerHomeActivity
import com.example.munch.adapter.CustomerCateringAndaAdapter
import com.example.munch.adapter.CustomerHistoryPemesananAdapter
import com.example.munch.adapter.CustomerUserAdapter
import com.example.munch.adapter.DetailPemesananAdapter
import com.example.munch.api.Retrofit
import com.example.munch.api.pesanan.PesananStore
import com.example.munch.api.user.UserStore
import com.example.munch.databinding.FragmentCustomerHomeBinding
import com.example.munch.helpers.FragmentUtils.isSafeFragment
import com.example.munch.model.DetailPemesanan
import com.example.munch.model.HistoryPemesanan
import com.example.munch.model.User
import kotlinx.coroutines.launch
import java.time.LocalDate

class CustomerHomeFragment : Fragment() {
    private val TAG = "CustomerHomeFragment"
    private var _binding: FragmentCustomerHomeBinding? = null
    val binding get() = _binding!!

    val date = LocalDate.now()
    var reqMapCateringAnda : Map<String, String> = mapOf("month" to date.monthValue.toString(), "year" to date.year.toString(), "detail_status" to "terkirim")
    var reqMapTopCatering : Map<String, String> = mapOf("batch_size" to "5", "users_role" to "provider", "sort_column" to "users_rating", "sort_type" to "desc")
    var reqMapOrderAgain : Map<String, String> = mapOf("batch_size" to "5", "pemesanan_status" to "selesai")
    lateinit var pesananStore : PesananStore
    lateinit var userStore : UserStore
    lateinit var cateringAdapter : CustomerCateringAndaAdapter
    lateinit var userAdapter : CustomerUserAdapter
    lateinit var historyAdapter : CustomerHistoryPemesananAdapter
    var cateringAnda : ArrayList<DetailPemesanan?> = arrayListOf()
    var topCatering : List<User> = listOf()
    var orderAgain : List<HistoryPemesanan?> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pesananStore = PesananStore.getInstance(requireContext())
        userStore = UserStore.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCustomerHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Retrofit.coroutine.launch {
            try {
                cateringAnda = (pesananStore.fetchDelivery(reqMapCateringAnda).body()?.data as ArrayList<DetailPemesanan?>)
                Log.d(TAG, "CATERING ANDA: $cateringAnda")

                topCatering = userStore.fetchPaginate(reqMapTopCatering).body()?.data?.data!!
                Log.d(TAG, "TOP CATERING: $topCatering")

                orderAgain = pesananStore.fetchPaginate(reqMapOrderAgain).body()?.data?.data!!
                Log.d(TAG, "ORDER AGAIN: $orderAgain")

                if (isSafeFragment()) {
                    (context as Activity).runOnUiThread {
                        //CATERING ANDA
                        cateringAdapter = CustomerCateringAndaAdapter(cateringAnda)
                        binding.rvCusMyOrder.addItemDecoration(
                            DividerItemDecoration(
                                requireContext(),
                                DividerItemDecoration.HORIZONTAL
                            )
                        )
                        binding.rvCusMyOrder.adapter = cateringAdapter
                        binding.rvCusMyOrder.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                        cateringAdapter.notifyDataSetChanged()

                        cateringAdapter.onClickListener = fun (it: View, detail: DetailPemesanan) {
                            val popUp = PopupMenu(requireContext(), it)
                            popUp.menuInflater.inflate(R.menu.menu_popup_diterima, popUp.menu)
                            popUp.setOnMenuItemClickListener {
                                return@setOnMenuItemClickListener when(it.itemId) {
                                    R.id.menu_diterima -> {
                                        diterima(detail.detail_id)
                                        true
                                    }
                                    else -> {
                                        false
                                    }
                                }
                            }
                            popUp.show()
                        }

                        //TOP CATERING
                        userAdapter = CustomerUserAdapter(topCatering)
                        binding.rvCusTop.addItemDecoration(
                            DividerItemDecoration(
                                requireContext(),
                                DividerItemDecoration.HORIZONTAL
                            )
                        )
                        binding.rvCusTop.adapter = userAdapter
                        binding.rvCusTop.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                        userAdapter.notifyDataSetChanged()

                        userAdapter.onClickListener = fun (user: User) {
                            (activity as CustomerHomeActivity).toPesan(user.users_id)
                        }

                        //ORDER AGAIN
                        historyAdapter = CustomerHistoryPemesananAdapter(orderAgain as List<HistoryPemesanan>)
                        binding.rvCusOrderAgain.addItemDecoration(
                            DividerItemDecoration(
                                requireContext(),
                                DividerItemDecoration.VERTICAL
                            )
                        )
                        binding.rvCusOrderAgain.adapter = historyAdapter
                        binding.rvCusOrderAgain.layoutManager = LinearLayoutManager(requireContext())
                        historyAdapter.notifyDataSetChanged()

                        historyAdapter.onClickListener = fun (pemesanan: HistoryPemesanan) {
                            (activity as CustomerHomeActivity).toPesan(pemesanan.users_provider!!.users_id)
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "ERROR", e)
                if (isSafeFragment()) {
                    (context as Activity).runOnUiThread {
                        Toast.makeText(
                            requireContext(),
                            "Error fetching data",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun diterima(detail_id : ULong) {
        Retrofit.coroutine.launch {
            try {
                pesananStore.receive(detail_id)
                cateringAnda = (pesananStore.fetchDelivery(reqMapCateringAnda).body()?.data as ArrayList<DetailPemesanan?>)

                if (isSafeFragment()) {
                    (context as Activity).runOnUiThread {
                        cateringAdapter.data = cateringAnda
                        cateringAdapter.notifyDataSetChanged()
                        Toast.makeText(
                            requireContext(),
                            "Berhasil menerima pesanan",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (e: Exception) {
                if (isSafeFragment()) {
                    (context as Activity).runOnUiThread {
                        Toast.makeText(
                            requireContext(),
                            "Error menerima pesanan",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}