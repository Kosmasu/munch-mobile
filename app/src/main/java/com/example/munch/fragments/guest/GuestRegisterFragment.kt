package com.example.munch.fragments.guest

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import com.example.munch.api.Retrofit
import com.example.munch.api.auth.AuthStore
import com.example.munch.databinding.FragmentGuestRegisterBinding
import kotlinx.coroutines.launch
import okhttp3.FormBody
import retrofit2.HttpException

class GuestRegisterFragment : Fragment() {
  private val TAG = "GuestRegisterFragment"
  private var _binding: FragmentGuestRegisterBinding? = null
  private val binding get() = _binding!!

  private lateinit var authStore: AuthStore

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    authStore = AuthStore.getInstance(requireContext())
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
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
      else if (binding.etRegisterPassword.text.toString() != binding.etRegisterConfirmPassword.text.toString()) {
        Toast.makeText(context, "Password tidak sama", Toast.LENGTH_SHORT).show()
      }
      else if (!binding.cbTnc.isChecked) {
        Toast.makeText(context, "Terms and Condition have to be agreed to", Toast.LENGTH_SHORT)
          .show()
      }
      else {
        //doregister
        val body = FormBody.Builder()
          .add("users_nama",binding.etRegisterNama.text.toString())
          .add("users_email", binding.etRegisterEmail.text.toString())
          .add("users_password", binding.etRegisterPassword.text.toString())
          .add("users_password_confirmation", binding.etRegisterConfirmPassword.text.toString())
          .add("users_alamat", binding.etRegisterAlamat.text.toString())
          .add("users_telepon", binding.etRegisterNomorTelepon.text.toString())
          .add("users_role", binding.spRegisterRole.selectedItem.toString().lowercase())
          .add("tnc", binding.cbTnc.isChecked.toString())
          .build()
        Retrofit.coroutine.launch {
          try {
            val resp = authStore.register(body)
            if (resp.code() != 201) {
              throw HttpException(resp)
            }
            if (activity != null) {
              requireActivity().runOnUiThread{
                Toast.makeText(context, "register successfull", Toast.LENGTH_SHORT).show()
                binding.etRegisterNama.text.clear()
                binding.etRegisterEmail.text.clear()
                binding.etRegisterPassword.text.clear()
                binding.etRegisterConfirmPassword.text.clear()
                binding.etRegisterAlamat.text.clear()
                binding.etRegisterNomorTelepon.text.clear()
                binding.cbTnc.isChecked = false
              }
            }
          } catch (e: Exception) {
            Log.e(TAG, "onViewCreated: API Server error", e)
            // TODO message
//            if (e is HttpException) {
//              if (e.code() == 422) {
//                val valError = e.response()?.errorBody().toString()
//                Log.e(TAG, "onCreateView: $valError")
//              }
//            }
            requireActivity().runOnUiThread {
              Toast.makeText(requireContext(), "Server error", Toast.LENGTH_SHORT).show()
            }
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