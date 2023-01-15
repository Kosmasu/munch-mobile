package com.example.munch.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.example.munch.R
import com.example.munch.api.Retrofit
import com.example.munch.api.auth.AuthStore
import com.example.munch.databinding.ActivityCustomerHomeBinding
import com.example.munch.fragments.*
import com.example.munch.fragments.customer.*
import com.example.munch.model.HeaderCart
import kotlinx.coroutines.launch

class CustomerHomeActivity : AppCompatActivity() {
  private lateinit var binding: ActivityCustomerHomeBinding
  lateinit var authStore: AuthStore

  lateinit var homeFragment: CustomerHomeFragment
  lateinit var searchFragment: CustomerSearchFragment
  lateinit var cartFragment: CustomerCartFragment
  lateinit var historyFragment: CustomerHistoryFragment
  lateinit var profileFragment: CustomerProfileFragment
  lateinit var topupFragment: CustomerTopupFragment

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityCustomerHomeBinding.inflate(layoutInflater)
    setContentView(binding.root)
    authStore = AuthStore.getInstance(this)

    homeFragment = CustomerHomeFragment()
    searchFragment = CustomerSearchFragment()
    cartFragment = CustomerCartFragment()
    historyFragment = CustomerHistoryFragment()
    profileFragment = CustomerProfileFragment()
    topupFragment = CustomerTopupFragment()
    swapFragment(homeFragment, "CustomerHomeFragment")

    binding.bnvCustomer.setOnItemSelectedListener {
      when (it.title.toString().lowercase()) {
        "home" -> {
          swapFragment(homeFragment, "CustomerHomeFragment")
          true
        }
        "search" -> {
          swapFragment(searchFragment, "CustomerSearchFragment")
          true
        }
        "cart" -> {
          toCart()
          true
        }
        "history" -> {
          swapFragment(historyFragment, "CustomerHistoryFragment")
          true
        }
        else -> {
          false
        }
      }
    }
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_option_customer, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.opt_customer_logout -> {
        Retrofit.coroutine.launch {
          authStore.logout()
          runOnUiThread {
            finish()
          }
        }
        return true
      }
      R.id.opt_customer_profile -> {
        swapFragment(profileFragment, "CustomerProfileFragment")
        return true
      }
      else -> {
        return false
      }
    }
  }

  private fun swapFragment(fragment: Fragment, tag: String) {
    supportFragmentManager.beginTransaction().apply {
      replace(binding.flFragmentCustomer.id, fragment, tag)
      setReorderingAllowed(true)
      addToBackStack(tag)
      commit()
    }
  }

  fun toTopup() {
    swapFragment(topupFragment, "CustomerTopupFragment")
  }

  fun toCart() {
    swapFragment(cartFragment, "CustomerCartFragment")
  }

  fun toDetailPemesanan(pemesanan_id: ULong) {
    supportFragmentManager.beginTransaction().apply {
      replace(binding.flFragmentCustomer.id, DetailPemesananFragment(pemesanan_id), "CustomerDetailHistoryFragment")
      setReorderingAllowed(true)
      addToBackStack("CustomerDetailHistoryFragment")
      commit()
    }
  }

  fun toDetailCart(cart: HeaderCart) {
    supportFragmentManager.beginTransaction().apply {
      replace(binding.flFragmentCustomer.id, CustomerCartDetailsFragment(cart), "CustomerCartDetailsFragment")
      setReorderingAllowed(true)
      addToBackStack("CustomerCartDetailsFragment")
      commit()
    }
  }

  fun toProvider(provider_id: ULong) {
    supportFragmentManager.beginTransaction().apply {
      replace(binding.flFragmentCustomer.id, CustomerMenuProviderFragment(provider_id), "CustomerMenuProviderFragment")
      setReorderingAllowed(true)
      addToBackStack("CustomerMenuProviderFragment")
      commit()
    }
  }

  fun toPesan(provider_id: ULong) {
    supportFragmentManager.beginTransaction().apply {
      replace(binding.flFragmentCustomer.id, CustomerMenuPesanFragment(provider_id), "CustomerMenuPesanFragment")
      setReorderingAllowed(true)
      addToBackStack("CustomerMenuPesanFragment")
      commit()
    }
  }
}