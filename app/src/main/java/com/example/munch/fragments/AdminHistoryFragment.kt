package com.example.munch.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.munch.R
import com.example.munch.databinding.FragmentAdminHistoryBinding
import com.example.munch.databinding.FragmentAdminProviderBinding

class AdminHistoryFragment : Fragment() {
  var _binding: FragmentAdminHistoryBinding? = null
  val binding get() = _binding!!

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?,
  ): View? {
    _binding = FragmentAdminHistoryBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}