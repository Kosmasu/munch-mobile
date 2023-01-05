package com.example.munch.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.munch.R
import androidx.fragment.app.Fragment
import com.example.munch.databinding.ActivityProviderHomeBinding
import com.example.munch.fragments.ProviderHistoryFragment
import com.example.munch.fragments.ProviderHomeFragment
import com.example.munch.fragments.ProviderMenusFragment

class ProviderHomeActivity : AppCompatActivity() {
  private lateinit var binding: ActivityProviderHomeBinding

  lateinit var homeFragment: ProviderHomeFragment
  lateinit var menuFragment: ProviderMenusFragment
  lateinit var historyFragment: ProviderHistoryFragment
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityProviderHomeBinding.inflate(layoutInflater)
    setContentView(binding.root)

    // Fragment init
    homeFragment = ProviderHomeFragment.newInstance()
    menuFragment = ProviderMenusFragment.newInstance()
    historyFragment = ProviderHistoryFragment.newInstance()
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

  private fun swapFragment(fragment: Fragment, tag: String) {
    supportFragmentManager.beginTransaction().apply {
      replace(binding.flFragmentProvider.id, fragment , tag)
      setReorderingAllowed(true)
      addToBackStack(tag)
      commit()
    }
  }
}