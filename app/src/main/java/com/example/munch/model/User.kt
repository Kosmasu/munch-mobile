package com.example.munch.model



data class User (
  val users_id: ULong,
  val users_email: String?,
  val users_telepon: String?,
  val users_nama: String?,
  val users_alamat: String?,
  val users_password: String?,
  val users_desc: String?,
  val users_saldo: Long?,
  val users_role: String?,
  val users_status: String?,
  val users_photo: String?,
  val users_rating: Int,
  val created_at: String?,
  val updated_at: String?,
  val deleted_at: String?,
  val menu: List<Menu>
)