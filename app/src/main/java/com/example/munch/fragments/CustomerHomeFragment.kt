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
    var reqMapTopCatering : Map<String, Any> = mapOf("batch_size" to "5", "sort" to mapOf("column" to "users_rating", "type" to "desc"))
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
                Log.d(TAG, "CATERING ANDA: $cateringAnda")

//                topCatering = userStore.fetchUnpaginated(reqMapTopCatering).body()?.data!!
//                Log.d(TAG, "TOP CATERING: $topCatering")

                orderAgain = pesananStore.fetchUnpaginated(reqMapOrderAgain).body()?.data!!
                Log.d(TAG, "ORDER AGAIN: $orderAgain")

                if (isSafeFragment()) {
                    (context as Activity).runOnUiThread {

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
}