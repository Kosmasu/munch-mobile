package com.example.munch.model

import java.sql.Timestamp
import java.time.LocalDateTime

data class Cart(
  val cart_id: ULong,
  val users_customer: ULong,
  val users_provider: ULong,
  val menu_id: ULong,
  val cart_jumlah: Int?,
  val cart_total: Long?,
  val cart_tanggal: LocalDateTime?,
  val created_at: Timestamp?,
  val updated_at: Timestamp?,
)
