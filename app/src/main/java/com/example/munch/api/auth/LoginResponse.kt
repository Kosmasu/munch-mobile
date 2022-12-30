package com.example.munch.api.auth

data class LoginResponse (
  val users_id: ULong,
  val users_email: String?,
  val users_role: String?,
  val access_token: String?,
  val token_type: String?,
)