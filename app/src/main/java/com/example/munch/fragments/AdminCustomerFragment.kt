package com.example.munch.fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.munch.R
import com.example.munch.adapter.AdminUserAdapter
import com.example.munch.api.Retrofit
import com.example.munch.api.user.UserStore
import com.example.munch.databinding.FragmentAdminCustomerBinding
import com.example.munch.model.User
import kotlinx.coroutines.launch

class AdminCustomerFragment : Fragment() {
  var _binding: FragmentAdminCustomerBinding? = null
  val binding get() = _binding!!

  var reqMap : Map<String, String> = mapOf("users_nama" to "", "users_role" to "customer")
  var listCustomer : List<User> = listOf()
  lateinit var customerAdapter : AdminUserAdapter
  lateinit var userStore : UserStore

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

    userStore = UserStore.getInstance(requireContext())
    Retrofit.coroutine.launch {
      listCustomer = userStore.fetchUnpaginated(reqMap).data

      (requireContext() as Activity).runOnUiThread{
        customerAdapter = AdminUserAdapter(listCustomer)
        binding.rvListCustomer.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        binding.rvListCustomer.adapter = customerAdapter
        binding.rvListCustomer.layoutManager = LinearLayoutManager(requireContext())
        customerAdapter.notifyDataSetChanged()

        customerAdapter.onClickListener = fun (it: View, position: Int, user: User) {
          val popUp = PopupMenu(requireContext(), it)
          popUp.menuInflater.inflate(R.menu.menu_popup_ban_unban, popUp.menu)
          if (user.users_status == "aktif") {
            popUp.menu.removeItem(R.id.menu_popup_unban)
          } else {
            popUp.menu.removeItem(R.id.menu_popup_ban)
          }
          popUp.setOnMenuItemClickListener {
            return@setOnMenuItemClickListener when(it.itemId) {
              R.id.menu_popup_ban -> {
                ban(user.users_id, requireContext())
                true
              }
              R.id.menu_popup_unban -> {
                unban(user.users_id, requireContext())
                true
              }
              else -> {
                false
              }
            }
          }
          popUp.show()
        }
      }
    }

    binding.btnSearchCustomer.setOnClickListener {
      reqMap = mapOf("users_nama" to binding.etSearchCustomer.text.toString(), "users_role" to "customer")
      Retrofit.coroutine.launch {
        listCustomer = userStore.fetchUnpaginated(reqMap).data
        (context as Activity).runOnUiThread {
          customerAdapter.data = listCustomer
          customerAdapter.notifyDataSetChanged()
        }
      }
    }
  }

  fun ban(id: ULong, context: Context) {
    Retrofit.coroutine.launch {
      userStore.ban(id)
      listCustomer = userStore.fetchUnpaginated(reqMap).data
      (context as Activity).runOnUiThread {
        customerAdapter.data = listCustomer
        customerAdapter.notifyDataSetChanged()
      }
    }
  }

  fun unban(id: ULong, context: Context) {
    Retrofit.coroutine.launch {
      userStore.unban(id)
      listCustomer = userStore.fetchUnpaginated(reqMap).data
      (context as Activity).runOnUiThread {
        customerAdapter.data = listCustomer
        customerAdapter.notifyDataSetChanged()
      }
    }
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}