package com.example.munch.fragments

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.munch.api.Retrofit
import com.example.munch.api.pesanan.PesananStore
import com.example.munch.api.user.UserStore
import com.example.munch.databinding.FragmentCustomerHomeBinding
import com.example.munch.model.DetailPemesanan
import com.example.munch.model.HistoryPemesanan
import com.example.munch.model.User
import kotlinx.coroutines.launch

class CustomerHomeFragment : Fragment() {
    private var _binding: FragmentCustomerHomeBinding? = null
    val binding get() = _binding!!

    var reqMapCateringAnda : Map<String, String> = mapOf("month" to "1", "year" to "2023", "detail_status" to "terkirim")
    var reqMapTopCatering : Map<String, String> = mapOf("batch_size" to "5", "sort.column" to "users_rating", "sort.type" to "desc")
    var reqMapOrderAgain : Map<String, String> = mapOf("batch_size" to "5", "pemesanan_status" to "selesai")
    lateinit var pesananStore : PesananStore
    lateinit var userStore : UserStore
//    lateinit var detailAdapter : CustomerDetailPemesananAdapter
//    lateinit var userAdapter : CustomerUserAdapter
//    lateinit var historyAdapter : CustomerHistoryPemesananAdapter
    var cateringAnda : List<DetailPemesanan> = listOf()
    var topCatering : List<User> = listOf()
    var orderAgain : List<HistoryPemesanan> = listOf()

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
                cateringAnda = pesananStore.fetchDelivery(reqMapCateringAnda).body()?.data!!

                (context as Activity).runOnUiThread {
                    println("CATERING ANDA : $cateringAnda")
                }
            } catch (e: Exception) {
                Log.e("cateringAnda", "cateringAnda", e)
                (context as Activity).runOnUiThread {
                    Toast.makeText(requireContext(), "Error fetching cateringAnda", Toast.LENGTH_SHORT).show()
                }
            }
        }

        Retrofit.coroutine.launch {
            try {
                topCatering = userStore.fetchUnpaginated(reqMapTopCatering).body()?.data!!

                (context as Activity).runOnUiThread {
                    println("TOP CATERING : $topCatering")
                }
            } catch (e: Exception) {
                Log.e("topCatering", "topCatering", e)
                (context as Activity).runOnUiThread {
                    Toast.makeText(requireContext(), "Error fetching topCatering", Toast.LENGTH_SHORT).show()
                }
            }
        }

        Retrofit.coroutine.launch {
            try {
                orderAgain = pesananStore.fetchUnpaginated(reqMapOrderAgain).body()?.data!!

                (context as Activity).runOnUiThread {
                    println("ORDER AGAIN : $orderAgain")
                }
            } catch (e: Exception) {
                Log.e("orderAgain", "orderAgain", e)
                (context as Activity).runOnUiThread {
                    Toast.makeText(requireContext(), "Error fetching orderAgain", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}