package com.example.munch.model



data class HistoryLog(
  val log_id: ULong,
  val log_level: String?, //enum("debug", "info", "notice", "warning", "error", "critical", "alert", "emergency")
  val log_title: String?,
  val log_desc: String?,
  val users_id: ULong,
  val log_timestamp: String?,
  val users: User?,
)
