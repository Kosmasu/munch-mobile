package com.example.munch.model

import com.example.munch.model.enum_class.PemesananStatus
import java.sql.Timestamp

data class HistoryPemesanan(
  val pemesanan_id: ULong,
  val users_provider: ULong,
  val users_customer: ULong,
  val pemesanan_jumlah: Int?,
  val pemesanan_total: Long?,
  val pemesanan_status: PemesananStatus?, //enum("menunggu", "ditolak", "diterima", "selesai")
  val pemesanan_rating: Int?,
  val created_at: Timestamp?,
  val updated_at: Timestamp?,
)
