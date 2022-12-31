package com.example.munch.model

data class Paginate<T>(
  val current_page: Int?,
  val data: List<T>?,
  val first_page_url: String?,
  val from: String?,
  val last_page: Int?,
  val last_page_url: String?,
  val total: Int?,
  val per_page: Int?,
)
