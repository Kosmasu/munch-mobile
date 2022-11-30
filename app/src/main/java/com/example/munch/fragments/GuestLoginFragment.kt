package com.example.munch.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.munch.databinding.FragmentGuestLoginBinding

class GuestLoginFragment(
  val login: (String, String)->Unit,
) : Fragment() {
  private var _binding: FragmentGuestLoginBinding? = null
  private val binding get() = _binding!!

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?,
  ): View? {
    _binding = FragmentGuestLoginBinding.inflate(inflater, container, false)
    binding.btnLogin.setOnClickListener {
      if (
        binding.etLoginEmail.text.isEmpty()
        || binding.etLoginPassword.text.isEmpty()
      ) {
        Toast.makeText(requireContext(), "Tidak boleh ada field yang kosong!", Toast.LENGTH_SHORT).show()
      }
      else {
        //login
        login(binding.etLoginEmail.text.toString(), binding.etLoginPassword.text.toString())
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