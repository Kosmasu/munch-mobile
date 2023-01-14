package com.example.munch.model




data class HistoryTopUp(
  val topup_id: ULong,
  val topup_nominal: Long?,
  val topup_tanggal: String?,
  val topup_response_code: Int?,
  val topup_response: String?,
  val users_id: ULong,
  val created_at: String?,
  val updated_at: String?,
)
