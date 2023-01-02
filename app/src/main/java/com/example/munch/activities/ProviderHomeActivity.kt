package com.example.munch.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.munch.databinding.ActivityProviderHomeBinding

class ProviderHomeActivity : AppCompatActivity() {
  private lateinit var binding: ActivityProviderHomeBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityProviderHomeBinding.inflate(layoutInflater)
    setContentView(binding.root)

    // Fragment init


  }

  fun swapFragment(fragment: Fragment, tag: String) {
    supportFragmentManager.beginTransaction().apply {
      replace(binding.flFragmentProvider.id, fragment , tag)
      setReorderingAllowed(true)
      addToBackStack(tag)
      commit()
    }
  }
}