package com.example.munch.api

data class Response<T>(
  val status: String,
  val message: String,
  val data: T,
) {
}