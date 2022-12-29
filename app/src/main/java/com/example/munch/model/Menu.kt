package com.example.munch.model

import com.example.munch.model.enum_class.MenuStatus
import java.sql.Timestamp

data class Menu(
  val menu_id: ULong?,
  val menu_nama: String?,
  val menu_foto: String?,
  val menu_harga: Long?,
  val menu_status: MenuStatus?, //enum("tersedia", "tidak tersedia")
  val users_id: ULong?,
  val created_at: Timestamp?,
  val updated_at: Timestamp?,
  val deleted_at: Timestamp?,
)
