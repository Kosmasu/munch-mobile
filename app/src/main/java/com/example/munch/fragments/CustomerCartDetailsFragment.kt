package com.example.munch.fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.munch.activities.CustomerHomeActivity
import com.example.munch.adapter.CustomerCartDetailsAdapter
import com.example.munch.api.Retrofit
import com.example.munch.api.cart.CartStore
import com.example.munch.api.pesanan.PesananStore
import com.example.munch.databinding.FragmentCustomerCartDetailsBinding
import com.example.munch.helpers.CurrencyUtils.toRupiah
import com.example.munch.model.Cart
import com.example.munch.model.HeaderCart
import kotlinx.coroutines.launch

class CustomerCartDetailsFragment(private var cart: HeaderCart) : Fragment() {
    private val TAG = "CustomerCartDetailsFragmnet"
    private var _binding : FragmentCustomerCartDetailsBinding?= null
    val binding get() = _binding!!
    
    lateinit var cartStore: CartStore
    lateinit var pesananStore: PesananStore
    lateinit var cartDetailsAdapter: CustomerCartDetailsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cartStore = CartStore.getInstance(requireContext())
        pesananStore = PesananStore.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCustomerCartDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cartDetailsAdapter = CustomerCartDetailsAdapter(cart.cart_provider!!)
        binding.rvListCartListPesanan.adapter = cartDetailsAdapter
        binding.rvListCartListPesanan.layoutManager = LinearLayoutManager(requireContext())
        cartDetailsAdapter.onClickListener = fun (cartDetails: Cart) {
            Retrofit.coroutine.launch { 
                try {
                    cartStore.delete(cartDetails.cart_id)

                    (context as Activity).runOnUiThread {
                        Toast.makeText(requireContext(), "Berhasil delete pesanan", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "DELETE PESANAN", e)
                    (context as Activity).runOnUiThread {
                        Toast.makeText(requireContext(), "Error delete pesanan", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        
        binding.tvListCartProvider.text = cart.users_nama
        binding.tvListCartTotal.text = "Total: ${cart.sum_cart_total?.toRupiah()},00"
        binding.tvListCartJumlah.text = "Jumlah Pesanan: ${cart.sum_cart_jumlah}"
        binding.btnListCartPesan.setOnClickListener {
            Retrofit.coroutine.launch {
                try {
                    pesananStore.pesanCart()

                    (context as Activity).runOnUiThread {
                        Toast.makeText(requireContext(), "Berhasil pesan", Toast.LENGTH_SHORT).show()
                        (activity as CustomerHomeActivity).supportFragmentManager.popBackStack()
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "PESAN CART", e)
                    (context as Activity).runOnUiThread {
                        Toast.makeText(requireContext(), "Error pesan", Toast.LENGTH_SHORT).show()
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