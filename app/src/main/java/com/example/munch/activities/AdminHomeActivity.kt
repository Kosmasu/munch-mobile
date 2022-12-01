
package com.example.munch.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.munch.R
import com.example.munch.databinding.ActivityAdminHomeBinding

class AdminHomeActivity : AppCompatActivity() {
  private lateinit var binding: ActivityAdminHomeBinding
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityAdminHomeBinding.inflate(layoutInflater)
    setContentView(binding.root)
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