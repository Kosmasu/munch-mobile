package com.example.munch.api.auth

data class MyStatResponse (
  val customers_count: Long?,
  val providers_count: Long?,
  val unverified_count: Long?,
  val thismonth_delivery: Long?,
  val total_pendapatan: Long?,
  val made_delivery: Long?,
)