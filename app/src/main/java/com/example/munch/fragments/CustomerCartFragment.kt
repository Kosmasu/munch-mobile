package com.example.munch.fragments

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.munch.activities.CustomerHomeActivity
import com.example.munch.adapter.CustomerCartAdapter
import com.example.munch.api.Retrofit
import com.example.munch.api.cart.CartStore
import com.example.munch.databinding.FragmentCustomerCartBinding
import com.example.munch.helpers.FragmentUtils.isSafeFragment
import com.example.munch.model.HeaderCart
import kotlinx.coroutines.launch

class CustomerCartFragment : Fragment() {
    private var _binding: FragmentCustomerCartBinding? = null
    val binding get() = _binding!!

    lateinit var cartStore : CartStore
    lateinit var cartAdapter : CustomerCartAdapter
    var listCart : List<HeaderCart> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cartStore = CartStore.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCustomerCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Retrofit.coroutine.launch {
            try {
                listCart = cartStore.fetchUnpaginated().body()?.data!!

                if (isSafeFragment()) {
                    (context as Activity).runOnUiThread {
                        cartAdapter = CustomerCartAdapter(listCart)
                        binding.rvCartList.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
                        binding.rvCartList.adapter = cartAdapter
                        binding.rvCartList.layoutManager = LinearLayoutManager(requireContext())
                        cartAdapter.notifyDataSetChanged()

                        cartAdapter.onClickListener = fun (cart: HeaderCart) {
                            (activity as CustomerHomeActivity).toDetailCart(cart)
                        }
                    }
                }
            } catch (e: Exception) {
                if (isSafeFragment()) {
                    Log.e("CART ERROR:", "cart", e)
                    (context as Activity).runOnUiThread {
                        Toast.makeText(requireContext(), "Error fetch cart", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        binding.btnClearCart.setOnClickListener {
            Retrofit.coroutine.launch {
                try {
                    cartStore.clear()
                    listCart = cartStore.fetchUnpaginated().body()?.data!!

                    if (isSafeFragment()) {
                        (context as Activity).runOnUiThread {
                            cartAdapter.data = listCart
                            cartAdapter.notifyDataSetChanged()
                            Toast.makeText(requireContext(), "Cart cleared", Toast.LENGTH_SHORT).show()
                        }
                    }
                } catch (e: Exception) {
                    if (isSafeFragment()) {
                        (context as Activity).runOnUiThread {
                            Toast.makeText(requireContext(), "Error clear cart", Toast.LENGTH_SHORT).show()
                        }
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