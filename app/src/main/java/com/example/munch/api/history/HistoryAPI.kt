package com.example.munch.api.history

import com.example.munch.model.*
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface HistoryAPI {
  @GET("log")
  fun logPaginate(@QueryMap parameters: Map<String, String>): Response<Paginate<List<HistoryLog>>>

  @GET("log")
  fun logUnpaginated(@QueryMap parameters: Map<String, String>): Response<List<HistoryLog>>

  @GET("historyMenu")
  fun menuPaginate(@QueryMap parameters: Map<String, String>): Response<Paginate<List<HistoryMenu>>>

  @GET("historyMenu")
  fun menuUnpaginated(@QueryMap parameters: Map<String, String>): Response<List<HistoryMenu>>

  @GET("historyTopup")
  fun topUpPaginate(@QueryMap parameters: Map<String, String>): Response<Paginate<List<HistoryPemesanan>>>

  @GET("historyTopup")
  fun topUpUnpaginated(@QueryMap parameters: Map<String, String>): Response<List<HistoryPemesanan>>
}
