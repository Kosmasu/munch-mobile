package com.example.munch.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.munch.R
import com.example.munch.databinding.FragmentAdminHomeBinding
import com.example.munch.databinding.FragmentProviderHomeBinding
import com.example.munch.databinding.FragmentProviderProfileBinding


class ProviderProfileFragment : Fragment() {
    var _binding: FragmentProviderProfileBinding? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProviderProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}