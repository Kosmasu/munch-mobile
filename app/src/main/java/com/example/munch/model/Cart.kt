package com.example.munch.model




data class Cart(
  val cart_id: ULong,
  val users_customer: ULong,
  val users_provider: ULong,
  val menu_id: ULong,
  val cart_jumlah: Int?,
  val cart_total: Long?,
  val cart_tanggal: String?,
  val menu: Menu?,
  val customer: User?,
  val created_at: String?,
  val updated_at: String?,
)
