package com.example.munch.model

data class Response<T>(
  val response: retrofit2.Response<Body<T>>
) {
  class Body<T>(
    val status: String,
    val message: String,
    val data: T,
  )
}