package com.example.munch.api.pesanan

data class RequestBodyDetailMenu(
  val menu_id: ULong,
  val detail_jumlah: ULong,
  val detail_tanggal: String?,
)
