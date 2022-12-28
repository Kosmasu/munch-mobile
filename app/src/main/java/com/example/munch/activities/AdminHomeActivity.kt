
package com.example.munch.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.munch.R
import com.example.munch.databinding.ActivityAdminHomeBinding
import com.example.munch.fragments.*

class AdminHomeActivity : AppCompatActivity() {
  private lateinit var binding: ActivityAdminHomeBinding
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityAdminHomeBinding.inflate(layoutInflater)
    setContentView(binding.root)


    supportFragmentManager.beginTransaction().apply {
      replace(binding.flFragmentAdmin.id,AdminHomeFragment() , "AdminHomeFragment")
      setReorderingAllowed(true)
      commit()
    }

    binding.bnvAdmin.setOnItemSelectedListener {
      when (it.title.toString().lowercase()) {
        "home" -> {
          supportFragmentManager.beginTransaction().apply {
            replace(binding.flFragmentAdmin.id, AdminHomeFragment(), "GuestLoginFragment")
            setReorderingAllowed(true)
            commit()
          }
        }
        "customer" -> {
          supportFragmentManager.beginTransaction().apply {
            replace(binding.flFragmentAdmin.id,AdminCustomerFragment() , "AdminHomeFragment")
            setReorderingAllowed(true)
            commit()
          }
        }
        "provider" -> {
          supportFragmentManager.beginTransaction().apply {
            replace(binding.flFragmentAdmin.id,AdminProviderFragment() , "AdminHomeFragment")
            setReorderingAllowed(true)
            commit()
          }
        }
        "history" -> {
          supportFragmentManager.beginTransaction().apply {
            replace(binding.flFragmentAdmin.id,AdminHistoryFragment() , "AdminHomeFragment")
            setReorderingAllowed(true)
            commit()
          }
        }
      }
      return@setOnItemSelectedListener true
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
}