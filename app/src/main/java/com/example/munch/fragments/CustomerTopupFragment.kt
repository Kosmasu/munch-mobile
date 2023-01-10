package com.example.munch.fragments

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.munch.activities.CustomerHomeActivity
import com.example.munch.api.Retrofit
import com.example.munch.api.auth.AuthStore

import com.example.munch.databinding.FragmentCustomerTopupBinding
import kotlinx.coroutines.launch
import okhttp3.FormBody

class CustomerTopupFragment : Fragment() {
    private var _binding: FragmentCustomerTopupBinding? = null
    val binding get() = _binding!!

    lateinit var authStore : AuthStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authStore = AuthStore.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCustomerTopupBinding.inflate(inflater, container, false)

        binding.btnTopup.setOnClickListener {
            if (binding.etTopupNominal.text.isBlank() || binding.etTopupPassword.text.isBlank()) {
                Toast.makeText(requireContext(), "Field kosong!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            Retrofit.coroutine.launch {
                try {
                    authStore.topup(
                        FormBody.Builder()
                            .add("topup_amount", binding.etTopupNominal.text.toString())
                            .add("password", binding.etTopupPassword.text.toString())
                            .build()
                    )

                    (context as Activity).runOnUiThread {
                        Toast.makeText(requireContext(), "Berhasil topup", Toast.LENGTH_SHORT).show()
                        (activity as CustomerHomeActivity).supportFragmentManager.popBackStack()
                    }
                } catch (e: Exception) {
                    Log.e("TOPUP", e.printStackTrace().toString())
                    (context as Activity).runOnUiThread {
                        Toast.makeText(requireContext(), "Error topup", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}