package com.example.munch.api.report

import com.example.munch.model.*
import retrofit2.Response
import retrofit2.http.GET

interface ReportAPI {
  @GET("report/penjualan")
  suspend fun penjualan(): Response<Result<String?>>
}
