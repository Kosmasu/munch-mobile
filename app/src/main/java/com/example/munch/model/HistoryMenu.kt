package com.example.munch.model



data class HistoryMenu(
  val history_menu_id: ULong,
  val history_menu_action: String?,
  val menu_id: ULong,
  val created_at: String?,
  val updated_at: String?,
  val users: User?,
)
