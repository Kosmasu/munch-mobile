package com.example.munch.fragments

import android.graphics.Paint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import com.example.munch.R
import com.example.munch.databinding.FragmentGuestLoginBinding
import com.example.munch.databinding.FragmentGuestRegisterBinding

class GuestRegisterFragment : Fragment() {
  private var _binding: FragmentGuestRegisterBinding? = null
  private val binding get() = _binding!!

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    _binding = FragmentGuestRegisterBinding.inflate(inflater, container, false)

    binding.btnRegister.setOnClickListener {
      if (
        binding.etRegisterNama.text.isEmpty()
        || binding.etRegisterEmail.text.isEmpty()
        || binding.etRegisterPassword.text.isEmpty()
        || binding.etRegisterConfirmPassword.text.isEmpty()
        || binding.etRegisterAlamat.text.isEmpty()
        || binding.etRegisterNomorTelepon.text.isEmpty()
      ) {
        Toast.makeText(requireContext(), "Tidak boleh ada field yang kosong!", Toast.LENGTH_SHORT).show()
      }
      else if (
        !binding.etRegisterNomorTelepon.text.isDigitsOnly()
      ) {
        Toast.makeText(requireContext(), "Nomor telepon harus angka!", Toast.LENGTH_SHORT).show()
      }
      else {
        //doregister
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