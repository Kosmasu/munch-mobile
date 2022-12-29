package com.example.munch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.munch.activities.CustomerHomeActivity
import com.example.munch.api.AuthAPI
import com.example.munch.api.Retrofit
import com.example.munch.databinding.ActivityMainBinding
import com.example.munch.fragments.GuestLoginFragment
import com.example.munch.fragments.GuestRegisterFragment
import com.example.munch.model.enum_class.PemesananStatus
import kotlinx.coroutines.launch
import okhttp3.FormBody

class MainActivity : AppCompatActivity() {
  private lateinit var binding: ActivityMainBinding
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    //testing api

    val retrofit = Retrofit.getInstance()
    val retrofitAuth = retrofit.create(AuthAPI::class.java)
    Retrofit.coroutine.launch {
      val hasil = retrofitAuth.login(
        FormBody.Builder()
          .add("users_email", "kevin@kevin.com")
          .add("password", "123")
          .build()
      )
      println("hasil=${hasil.string()}")
//      println("hasil=${hasil.}")
      val hasil2 = retrofitAuth.login2(
        FormBody.Builder()
          .add("users_email", "kevin@kevin.com")
          .add("password", "123")
          .build()
      )
      println("hasil=${hasil2}")
//      println("hasil.data=${hasil2.user.users_id}")
    }
    println(PemesananStatus.DITOLAK)
    println(PemesananStatus.DITOLAK.name)
    println(PemesananStatus.DITOLAK.toString())
    println(PemesananStatus.DITOLAK.status)

    //testing api

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