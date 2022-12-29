package com.example.munch.model

import java.sql.Timestamp

data class HistoryMenu(
  val HistoryMenuId: ULong?,
  val HistoryMenuAction: String?,
  val MenuId: ULong?,
  val created_at: Timestamp?,
  val updated_at: Timestamp?,
)
