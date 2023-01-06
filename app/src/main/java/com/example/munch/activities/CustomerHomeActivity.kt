package com.example.munch.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.example.munch.R
import com.example.munch.databinding.ActivityCustomerHomeBinding
import com.example.munch.fragments.*

class CustomerHomeActivity : AppCompatActivity() {
  private lateinit var binding: ActivityCustomerHomeBinding

  val homeFragment = CustomerHomeFragment()
  val searchFragment = CustomerSearchFragment()
  val historyFragment = CustomerHistoryFragment()
  val topupFragment = CustomerTopupFragment()
  val profileFragment = CustomerProfileFragment()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityCustomerHomeBinding.inflate(layoutInflater)
    setContentView(binding.root)

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
        "history" -> {
          swapFragment(historyFragment, "CustomerHistoryFragment")
          true
        }
        "topup" -> {
          swapFragment(topupFragment, "CustomerTopupFragment")
          true
        }
        "profile" -> {
          swapFragment(profileFragment, "CustomerProfileFragment")
          true
        }
        else -> {
          false
        }
      }
    }
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_option_logout, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    finish()
    return super.onOptionsItemSelected(item)
  }

  private fun swapFragment(fragment: Fragment, tag: String) {
    supportFragmentManager.beginTransaction().apply {
      replace(binding.flFragmentCustomer.id, fragment, tag)
      setReorderingAllowed(true)
      addToBackStack(tag)
      commit()
    }
  }
}