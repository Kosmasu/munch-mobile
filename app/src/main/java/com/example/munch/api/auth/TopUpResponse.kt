package com.example.munch.api.auth

data class TopUpResponse (
  val topup_amount: ULong,
  val users_saldo: ULong,
)