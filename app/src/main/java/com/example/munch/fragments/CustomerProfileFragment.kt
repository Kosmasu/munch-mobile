package com.example.munch.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.munch.activities.CustomerHomeActivity
import com.example.munch.api.Retrofit
import com.example.munch.api.auth.AuthStore
import com.example.munch.databinding.FragmentCustomerProfileBinding
import com.example.munch.helpers.CurrencyUtils.toRupiah
import com.example.munch.model.User
import kotlinx.coroutines.launch

class CustomerProfileFragment : Fragment() {
    private var _binding: FragmentCustomerProfileBinding? = null
    val binding get() = _binding!!

    lateinit var authStore : AuthStore
    lateinit var me : User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authStore = AuthStore.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCustomerProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Retrofit.coroutine.launch {
            try {
                me = authStore.me().response.body()?.data!!

                requireActivity().runOnUiThread {
                    binding.etCustomerDetailNama.text = me.users_nama
                    binding.etCustomerDetailSaldo.text = "${me.users_saldo!!.toRupiah()},00"
                    binding.etCustomerDetailEmail.text = me.users_email
                    binding.etCustomerDetailNotel.text = me.users_telepon
                    binding.etCustomerDetailAlamat.text = me.users_alamat
                }
            } catch (e: Exception) {
                requireActivity().runOnUiThread {
                    Toast.makeText(requireContext(), "Error fetch stats", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.btnCustomerGoTopup.setOnClickListener {
            (activity as CustomerHomeActivity).toTopup()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}