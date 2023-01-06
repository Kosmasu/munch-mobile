
package com.example.munch.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.example.munch.R
import com.example.munch.databinding.ActivityAdminHomeBinding
import com.example.munch.fragments.*

class AdminHomeActivity : AppCompatActivity() {
  private lateinit var binding: ActivityAdminHomeBinding

  val homeFragment = AdminHomeFragment()
  val customerFragment = AdminCustomerFragment()
  val providerFragment = AdminProviderFragment()
  val historyFragment = AdminHistoryFragment()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityAdminHomeBinding.inflate(layoutInflater)
    setContentView(binding.root)

    swapFragment(homeFragment, "AdminHomeFragment")

    binding.bnvAdmin.setOnItemSelectedListener {
      when (it.title.toString().lowercase()) {
        "home" -> {
          swapFragment(homeFragment, "AdminHomeFragment")
          true
        }
        "customer" -> {
          swapFragment(customerFragment, "AdminCustomerFragment")
          true
        }
        "provider" -> {
          swapFragment(providerFragment, "AdminProviderFragment")
          true
        }
        "history" -> {
          swapFragment(historyFragment, "AdminHistoryFragment")
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
      replace(binding.flFragmentAdmin.id, fragment, tag)
      setReorderingAllowed(true)
      addToBackStack(tag)
      commit()
    }
  }
}