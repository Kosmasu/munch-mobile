package com.example.munch.model

import java.sql.Timestamp

data class HistoryPemesanan(
  val pemesanan_id: ULong,
  val users_provider: User?,
  val users_customer: User?,
  val pemesanan_jumlah: Int?,
  val pemesanan_total: Long?,
  val pemesanan_status: String?, //enum("menunggu", "ditolak", "diterima", "selesai")
  val pemesanan_rating: Int?,
  val created_at: String?,
  val updated_at: String?,
  val users: User?,
)
