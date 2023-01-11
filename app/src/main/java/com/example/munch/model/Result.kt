package com.example.munch.model

data class Result<T>(
  val status: String,
  val message: String,
  val data: T,
) {
}