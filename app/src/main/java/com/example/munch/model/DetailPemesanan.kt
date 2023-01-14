package com.example.munch.model



data class DetailPemesanan(
  val detail_id: ULong,
  val pemesanan_id: ULong,
  val history_pemesanan: HistoryPemesanan?,
  val menu_id: ULong,
  val menu: Menu?,
  val detail_jumlah: Int?,
  val detail_total: Long?,
  val detail_tanggal: String?,
  val detail_status: String?, //enum("belum dikirim", "terkirim", "diterima")
  val created_at: String?,
  val updated_at: String?,
)
