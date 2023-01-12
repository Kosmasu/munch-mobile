package com.example.munch.model

data class HeaderCart(
  val users_id: ULong,
  val users_nama: String?,
  val sum_cart_jumlah: ULong,
  val sum_cart_total: ULong,
  val users_rating: String?,
  val users_photo: String?,
  val cart_provider: List<Cart>?
)
