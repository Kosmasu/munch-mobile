package com.example.munch.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Token(
  @PrimaryKey
  val users_id: ULong,
  val access_token: String,
) {
  override fun toString(): String {
    return "$users_id - $access_token"
  }
}