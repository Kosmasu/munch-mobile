package com.example.munch.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Token(
  @PrimaryKey
  var users_id: Int,
  var access_token: String,
)