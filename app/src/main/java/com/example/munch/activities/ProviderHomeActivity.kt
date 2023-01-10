package com.example.munch.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.munch.R
import androidx.fragment.app.Fragment
import com.example.munch.api.Retrofit
import com.example.munch.api.auth.AuthStore
import com.example.munch.databinding.ActivityProviderHomeBinding
import com.example.munch.fragments.*
import kotlinx.coroutines.launch

class ProviderHomeActivity : AppCompatActivity() {
  private lateinit var binding: ActivityProviderHomeBinding
  lateinit var authStore: AuthStore

  lateinit var homeFragment: ProviderHomeFragment
  lateinit var menuFragment: ProviderMenusFragment
  lateinit var historyFragment: ProviderHistoryFragment
  lateinit var profileFragment: ProviderProfileFragment
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityProviderHomeBinding.inflate(layoutInflater)
    setContentView(binding.root)
    authStore = AuthStore.getInstance(this)

    // Fragment init
    homeFragment = ProviderHomeFragment.newInstance()
    menuFragment = ProviderMenusFragment.newInstance()
    historyFragment = ProviderHistoryFragment.newInstance()
    profileFragment = ProviderProfileFragment.newInstance()
    swapFragment(homeFragment,"ProviderHomeFragment")

    binding.bnvProvider.setOnItemSelectedListener {
      when (it.itemId){
        R.id.nav_provider_home -> {
          swapFragment(homeFragment,"ProviderHomeFragment")
          true
        }
        R.id.nav_provider_menu -> {
          swapFragment(menuFragment,"ProviderMenuFragment")
          true
        }
        R.id.nav_provider_history -> {
          swapFragment(historyFragment,"ProviderHistoryFragment")
          true
        }
        else -> {
          false
        }
      }
    }
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_option_provider, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.opt_provider_logout -> {
        Retrofit.coroutine.launch {
          authStore.logout()
          runOnUiThread {
            finish()
          }
        }
        return true
      }
      R.id.opt_provider_profile -> {
        swapFragment(profileFragment,"ProviderProfileFragment")
        return true
      }
      else -> {
        return false
      }
    }
  }

  private fun swapFragment(fragment: Fragment, tag: String) {
    supportFragmentManager.beginTransaction().apply {
      replace(binding.flFragmentProvider.id, fragment , tag)
      setReorderingAllowed(true)
      addToBackStack(tag)
      commit()
    }
  }
}