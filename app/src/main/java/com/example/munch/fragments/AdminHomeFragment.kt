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
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.munch.R
import com.example.munch.adapter.AdminUserAdapter
import com.example.munch.api.Retrofit
import com.example.munch.api.auth.AuthStore
import com.example.munch.api.auth.MyStatResponse
import com.example.munch.api.user.UserStore
import com.example.munch.databinding.FragmentAdminHomeBinding
import com.example.munch.model.Response
import com.example.munch.model.User
import kotlinx.coroutines.launch

class AdminHomeFragment : Fragment() {
  var _binding: FragmentAdminHomeBinding? = null
  val binding get() = _binding!!

  var reqMap : Map<String, String> = mapOf("users_role" to "provider", "users_status" to "menunggu")
  var listWaitingProvider : List<User> = listOf()
  var stats : MyStatResponse? = null
  lateinit var authStore : AuthStore
  lateinit var userStore : UserStore
  lateinit var providerAdapter : AdminUserAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?,
  ): View? {
    _binding = FragmentAdminHomeBinding.inflate(inflater, container, false)
    return binding.root
  }

  @SuppressLint("SetTextI18n")
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    authStore = AuthStore.getInstance(requireContext())
    userStore = UserStore.getInstance(requireContext())
    Retrofit.coroutine.launch {
      try {
        stats = authStore.myStat().data

        (requireContext() as Activity).runOnUiThread {
          binding.tvRegisteredAccounts.text = "${stats?.providers_count?.let {
            stats?.customers_count?.plus(
              it
            )
          }} Accounts"
          binding.tvCustomers.text = "${stats?.customers_count} Accounts"
          binding.tvProviders.text = "${stats?.providers_count} Accounts"
        }
      } catch (e: Exception) {
        (requireContext() as Activity).runOnUiThread {
          Toast.makeText(requireContext(), "Error fetching stats", Toast.LENGTH_SHORT).show()
        }
      }

      try {
        listWaitingProvider = userStore.fetchUnpaginated(reqMap).data

        (requireContext() as Activity).runOnUiThread {
          providerAdapter = AdminUserAdapter(listWaitingProvider)
          binding.rvListWaitingProvider.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
          binding.rvListWaitingProvider.adapter = providerAdapter
          binding.rvListWaitingProvider.layoutManager = LinearLayoutManager(requireContext())
          providerAdapter.notifyDataSetChanged()

          providerAdapter.onClickListener = fun (it: View, position: Int, user: User) {
            val popUp = PopupMenu(requireContext(), it)
            popUp.menuInflater.inflate(R.menu.menu_popup_approve, popUp.menu)
            popUp.setOnMenuItemClickListener {
              return@setOnMenuItemClickListener when(it.itemId) {
                R.id.menu_popup_approve -> {
                  approve(user.users_id, requireContext())
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
      } catch (e: Exception) {
        (requireContext() as Activity).runOnUiThread {
          Toast.makeText(requireContext(), "Error fetching waiting provider", Toast.LENGTH_SHORT).show()
        }
      }
    }
  }

  fun approve(id: ULong, context: Context) {
    Retrofit.coroutine.launch {
      try {
        userStore.approveProvider(id)
        listWaitingProvider = userStore.fetchUnpaginated(reqMap).data
        (context as Activity).runOnUiThread {
          providerAdapter.data = listWaitingProvider
          providerAdapter.notifyDataSetChanged()
        }
      } catch (e: Exception) {
        (context as Activity).runOnUiThread {
          Toast.makeText(requireContext(), "Error approve provider", Toast.LENGTH_SHORT).show()
        }
      }
    }
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}