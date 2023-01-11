package com.example.munch.fragments

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.munch.R
import com.example.munch.adapter.AdminUserAdapter
import com.example.munch.api.Retrofit
import com.example.munch.api.user.UserStore
import com.example.munch.databinding.FragmentAdminProviderBinding
import com.example.munch.model.User
import kotlinx.coroutines.launch

class AdminProviderFragment : Fragment() {
  private var _binding: FragmentAdminProviderBinding? = null
  val binding get() = _binding!!

  var reqMap : Map<String, String> = mapOf("users_nama" to "", "users_role" to "provider")
  var listProvider : List<User> = listOf()
  lateinit var providerAdapter : AdminUserAdapter
  lateinit var userStore : UserStore

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?,
  ): View {
    _binding = FragmentAdminProviderBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    userStore = UserStore.getInstance(requireContext())
    Retrofit.coroutine.launch {
      try {
        listProvider = userStore.fetchUnpaginated(reqMap).response.body()?.data!!

        (requireContext() as Activity).runOnUiThread {
          providerAdapter = AdminUserAdapter(listProvider)
          binding.rvListProvider.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
          binding.rvListProvider.adapter = providerAdapter
          binding.rvListProvider.layoutManager = LinearLayoutManager(requireContext())
          providerAdapter.notifyDataSetChanged()

          providerAdapter.onClickListener = fun (it: View, position: Int, user: User) {
            val popUp = PopupMenu(requireContext(), it)
            popUp.menuInflater.inflate(R.menu.menu_popup_ban_unban, popUp.menu)
            if (user.users_status == "aktif") {
              popUp.menu.removeItem(R.id.menu_popup_unban)
            } else if (user.users_status == "banned") {
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
            if (user.users_status != "menunggu") {
              popUp.show()
            }
          }
        }
      } catch (e: Exception) {
        (requireContext() as Activity).runOnUiThread {
          Toast.makeText(requireContext(), "Error fetching provider", Toast.LENGTH_SHORT).show()
        }
      }
    }

    binding.btnSearchProvider.setOnClickListener {
      reqMap = mapOf("users_nama" to binding.etSearchProvider.text.toString(), "users_role" to "provider")

      Retrofit.coroutine.launch {
        try {
          listProvider = userStore.fetchUnpaginated(reqMap).response.body()?.data!!
          (context as Activity).runOnUiThread {
            providerAdapter.data = listProvider
            providerAdapter.notifyDataSetChanged()
          }
        } catch (e: Exception) {
          (context as Activity).runOnUiThread {
            Toast.makeText(requireContext(), "Error fetching provider", Toast.LENGTH_SHORT).show()
          }
        }

      }
    }
  }

  fun ban(id: ULong, context: Context) {
    Retrofit.coroutine.launch {
      try {
        userStore.ban(id)
        listProvider = userStore.fetchUnpaginated(reqMap).response.body()?.data!!
        (context as Activity).runOnUiThread {
          providerAdapter.data = listProvider
          providerAdapter.notifyDataSetChanged()
        }
      } catch (e: Exception) {
        (context as Activity).runOnUiThread {
          Toast.makeText(requireContext(), "Error ban provider", Toast.LENGTH_SHORT).show()
        }
      }
    }
  }

  fun unban(id: ULong, context: Context) {
    Retrofit.coroutine.launch {
      try {
        userStore.unban(id)
        listProvider = userStore.fetchUnpaginated(reqMap).response.body()?.data!!
        (context as Activity).runOnUiThread {
          providerAdapter.data = listProvider
          providerAdapter.notifyDataSetChanged()
        }
      } catch (e: Exception) {
        (context as Activity).runOnUiThread {
          Toast.makeText(requireContext(), "Error unban provider", Toast.LENGTH_SHORT).show()
        }
      }
    }
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}