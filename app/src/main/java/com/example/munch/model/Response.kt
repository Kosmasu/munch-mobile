package com.example.munch.model

data class Response<T>(
  val status: String,
  val message: String,
  val data: T,
) {
}

//package com.example.munch.model
//
//data class Response<T>(
//  val status: String,
//  val message: String,
//  val data: T,
//) {
//}