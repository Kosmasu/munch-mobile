package com.example.munch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.munch.activities.ProviderHomeActivity
import com.example.munch.api.Retrofit
import com.example.munch.api.auth.AuthStore
import com.example.munch.databinding.ActivityMainBinding
import com.example.munch.fragments.GuestLoginFragment
import com.example.munch.fragments.GuestRegisterFragment
import kotlinx.coroutines.launch
import okhttp3.FormBody

class MainActivity : AppCompatActivity() {
  private lateinit var binding: ActivityMainBinding
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    //testing api


    val authStore = AuthStore.getInstance(this)
    Retrofit.coroutine.launch {
      val responseLogin = authStore.login(
        FormBody.Builder()
          .add("users_email", "provider@provider.com")
          .add("password", "123")
          .build()
      )
      println("responseLogin=${responseLogin}")

      val responseMe = authStore.me()
      println("responseMe = ${responseMe}")

      val responseMyStat = authStore.myStat()
      println("responseStat = ${responseMyStat}")
    }

    //testing api

    val login: (String, String)->Unit = { email: String, password: String ->
      // tambah pengecekan role
      startActivity(Intent(this@MainActivity, ProviderHomeActivity::class.java))
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