package com.example.munch.api.pesanan

data class RequestBodyDetailMenu(
  val menu_id: ULong,
  val detail_tanggal: String?,
  val detail_jumlah: Int = 1,
)
