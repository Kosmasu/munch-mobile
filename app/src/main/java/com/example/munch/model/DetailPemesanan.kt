package com.example.munch.model

import com.example.munch.model.enum_class.DetailPemesananStatus
import java.sql.Timestamp

data class DetailPemesanan(
  val detail_id: ULong,
  val pemesanan_id: ULong,
  val history_pemesanan: HistoryPemesanan?,
  val menu_id: ULong,
  val detail_jumlah: Int?,
  val detail_total: Long?,
  val detail_tanggal: String?,
  val detail_status: DetailPemesananStatus?, //enum("belum dikirim", "terkirim", "diterima")
  val created_at: Timestamp?,
  val updated_at: Timestamp?,
)
