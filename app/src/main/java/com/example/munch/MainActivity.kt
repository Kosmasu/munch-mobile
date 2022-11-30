package com.example.munch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.munch.activities.CustomerHomeActivity
import com.example.munch.databinding.ActivityMainBinding
import com.example.munch.fragments.GuestLoginFragment
import com.example.munch.fragments.GuestRegisterFragment

class MainActivity : AppCompatActivity() {
  private lateinit var binding: ActivityMainBinding
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    val login: (String, String)->Unit = { email: String, password: String ->
      // tambah pengecekan role
      startActivity(Intent(this@MainActivity, CustomerHomeActivity::class.java))
    }

    supportFragmentManager.beginTransaction().apply {
      replace(binding.flFragmentGuest.id, GuestLoginFragment(login), "GuestLoginFragment")
      setReorderingAllowed(true)
      commit()
    }

    binding.bnvGuest.setOnItemSelectedListener {
      when (it.title.toString().lowercase()) {
        "login" -> {
          supportFragmentManager.beginTransaction().apply {
            replace(binding.flFragmentGuest.id, GuestLoginFragment(login), "GuestLoginFragment")
            setReorderingAllowed(true)
            commit()
          }
        }
        "register" -> {
          supportFragmentManager.beginTransaction().apply {
            replace(binding.flFragmentGuest.id, GuestRegisterFragment(), "GuestRegisterFragment")
            setReorderingAllowed(true)
            commit()
          }
        }
      }
      return@setOnItemSelectedListener true
    }
  }
}