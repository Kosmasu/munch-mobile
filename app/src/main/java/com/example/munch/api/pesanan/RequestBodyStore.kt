package com.example.munch.api.pesanan

data class RequestBodyStore(
  val users_provider: ULong,
  val details: List<RequestBodyDetailMenu?>
)
