package com.example.munch.model

import com.example.munch.model.enum_class.DetailPemesananStatus
import java.sql.Timestamp
import java.time.LocalDateTime

data class DetailPemesanan(
  val detail_id: ULong,
  val pemesanan_id: ULong,
  val menu_id: ULong,
  val detail_jumlah: Int?,
  val detail_total: Long?,
  val detail_tanggal: LocalDateTime?,
  val detail_status: DetailPemesananStatus?, //enum("belum dikirim", "terkirim", "diterima")
  val created_at: Timestamp?,
  val updated_at: Timestamp?,
)
