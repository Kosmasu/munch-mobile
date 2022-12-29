package com.example.munch.model

import java.sql.Timestamp

data class User (
  val users_id: ULong?,
  val users_email: String?,
  val users_telepon: String?,
  val users_nama: String?,
  val users_alamat: String?,
  val users_password: String?,
  val users_desc: String?,
  val users_saldo: Long?,
  val users_status: String?,
  val created_at: Timestamp?,
  val updated_at: Timestamp?,
  val deleted_at: Timestamp?,
)