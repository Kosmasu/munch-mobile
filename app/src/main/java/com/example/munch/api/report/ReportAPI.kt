package com.example.munch.api.report

import com.example.munch.model.*
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface ReportAPI {
  @GET("report/penjualan")
  suspend fun penjualan(): Response<String?>
}