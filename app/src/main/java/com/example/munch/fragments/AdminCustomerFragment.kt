package com.example.munch.fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.munch.R
import com.example.munch.adapter.AdminUserAdapter
import com.example.munch.api.Retrofit
import com.example.munch.api.user.UserStore
import com.example.munch.databinding.FragmentAdminCustomerBinding
import com.example.munch.model.User
import kotlinx.coroutines.launch
import okhttp3.FormBody

class AdminCustomerFragment : Fragment() {
  var _binding: FragmentAdminCustomerBinding? = null
  val binding get() = _binding!!

  var reqMap : Map<String, String> = mapOf("users_role" to "customer")
  var listCustomer : List<User> = listOf()
  lateinit var customerAdapter : AdminUserAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
  }

  @SuppressLint("NotifyDataSetChanged")
  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?,
  ): View? {
    _binding = FragmentAdminCustomerBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    customerAdapter = AdminUserAdapter(listCustomer)
    binding.rvListCustomer.adapter = customerAdapter
    binding.rvListCustomer.layoutManager = LinearLayoutManager(view.context)

    val userStore = UserStore.getInstance(view.context)
    Retrofit.coroutine.launch {
      listCustomer = userStore.fetchUnpaginated(reqMap).data
      (view.context as Activity).runOnUiThread{
        println(listCustomer[0].users_nama)
        customerAdapter.notifyDataSetChanged()
      }
    }

  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}