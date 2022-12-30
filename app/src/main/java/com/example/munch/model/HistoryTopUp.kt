package com.example.munch.model

import java.sql.Timestamp
import java.time.LocalDateTime

data class HistoryTopUp(
  val topup_id: ULong,
  val topup_nominal: Long?,
  val topup_tanggal: LocalDateTime?,
  val topup_response_code: Int?,
  val topup_response: String?,
  val users_id: ULong,
  val created_at: Timestamp?,
  val updated_at: Timestamp?,
)
