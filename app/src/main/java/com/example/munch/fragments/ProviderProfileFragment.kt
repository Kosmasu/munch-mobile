package com.example.munch.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.munch.R
import com.example.munch.api.Retrofit
import com.example.munch.api.auth.AuthStore
import com.example.munch.databinding.FragmentProviderProfileBinding
import kotlinx.coroutines.launch


class ProviderProfileFragment : Fragment() {
    private val TAG = "ProviderProfileFragment"
    private var _binding: FragmentProviderProfileBinding? = null
    val binding get() = _binding!!
    lateinit var authStore: AuthStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authStore = AuthStore.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProviderProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Retrofit.coroutine.launch {
            try {
                val profile = authStore.me().data

                if (_binding != null){
                    requireActivity().runOnUiThread {
                        binding.etProviderDetailNama.text = profile!!.users_nama
                        binding.etProviderDetailEmail.text = profile.users_email
                        binding.etProviderDetailNotel.text = profile.users_telepon
                        binding.etProviderDetailAlamat.text = profile.users_alamat
                        binding.etProviderDetailDesc.setText(profile.users_desc)
                    }
                }

            } catch (e : Exception){
                Log.e(TAG, "onViewCreated: API Server Error", e)
                requireActivity().runOnUiThread {
                    Toast.makeText(requireContext(), "Server error", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // TODO: update profile
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment ProfileFragment.
         */
        @JvmStatic
        fun newInstance() =
            ProviderProfileFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}