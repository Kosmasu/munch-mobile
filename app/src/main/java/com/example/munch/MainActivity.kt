package com.example.munch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.munch.activities.AdminHomeActivity
import com.example.munch.activities.CustomerHomeActivity
import com.example.munch.activities.ProviderHomeActivity
import com.example.munch.api.Retrofit
import com.example.munch.api.auth.AuthStore
import com.example.munch.databinding.ActivityMainBinding
import com.example.munch.fragments.GuestLoginFragment
import com.example.munch.fragments.GuestRegisterFragment
import kotlinx.coroutines.launch
import okhttp3.FormBody

class MainActivity : AppCompatActivity() {
  private val TAG = "MainActivity"
  private lateinit var binding: ActivityMainBinding
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    //testing api


    val authStore = AuthStore.getInstance(this)
//    Retrofit.coroutine.launch {
//      val responseLogin = authStore.login(
//        FormBody.Builder()
//          .add("users_email", "provider@provider.com")
//          .add("password", "123")
//          .build()
//      )
//      println("responseLogin=${responseLogin}")
//
//      val responseMe = authStore.me()
//      println("responseMe = ${responseMe}")
//
//      val responseMyStat = authStore.myStat()
//      println("responseStat = ${responseMyStat}")
//    }

    //testing api

    val login: (String, String)->Unit = { email: String, password: String ->
      // pengecekan role
      var role: String
      Retrofit.coroutine.launch {
        try {
          val responseLogin = authStore.login(
            FormBody.Builder()
              .add("users_email", email)
              .add("password", password)
              .build()
          )
          role = responseLogin.data!!.users_role.toString()

          runOnUiThread {
            when (role) {
              "admin" -> startActivity(Intent(this@MainActivity, AdminHomeActivity::class.java))
              "provider" -> startActivity(Intent(this@MainActivity, ProviderHomeActivity::class.java))
              "customer" -> startActivity(Intent(this@MainActivity, CustomerHomeActivity::class.java))
              else -> {
                Toast.makeText(this@MainActivity, "API Server Error", Toast.LENGTH_SHORT).show()
                Log.e(TAG, "onCreate: Unknown role - $role")
              }
            }
          }

        } catch (e : Exception) {
          Log.e(TAG, "onViewCreated: API Server error", e)
          runOnUiThread {
            Toast.makeText(this@MainActivity, "Server error", Toast.LENGTH_SHORT).show()
          }
        }
      }
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