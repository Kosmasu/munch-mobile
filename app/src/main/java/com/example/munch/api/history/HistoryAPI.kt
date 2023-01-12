package com.example.munch.api.history

import com.example.munch.model.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface HistoryAPI {
  @GET("log")
  suspend fun logPaginate(@QueryMap parameters: Map<String, String?>): Response<Result<Paginate<HistoryLog>>>

  @GET("log")
  suspend fun logUnpaginated(@QueryMap parameters: Map<String, String?>): Response<Result<List<HistoryLog>>>

  @GET("historyMenu")
  suspend fun menuPaginate(@QueryMap parameters: Map<String, String?>): Response<Result<Paginate<HistoryMenu>>>

  @GET("historyMenu")
  suspend fun menuUnpaginated(@QueryMap parameters: Map<String, String?>): Response<Result<List<HistoryMenu>>>

  @GET("historyTopup")
  suspend fun topUpPaginate(@QueryMap parameters: Map<String, String?>): Response<Result<Paginate<HistoryTopUp>>>

  @GET("historyTopup")
  suspend fun topUpUnpaginated(@QueryMap parameters: Map<String, String?>): Response<Result<List<HistoryTopUp>>>
}
