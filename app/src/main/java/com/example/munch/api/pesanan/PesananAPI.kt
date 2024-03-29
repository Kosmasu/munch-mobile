package com.example.munch.api.pesanan

import com.example.munch.model.*
import retrofit2.Response
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface PesananAPI {
  @GET("pesanan")
  suspend fun fetchPaginate(@QueryMap parameters: Map<String, String?>): Response<Result<Paginate<HistoryPemesanan?>?>?>

  @GET("pesanan")
  suspend fun fetchUnpaginated(@QueryMap parameters: Map<String, String?>): Response<Result<List<HistoryPemesanan>?>?>

  @GET("pesanan/{pesanan_id}")
  suspend fun fetch(@Path("pesanan_id") pesanan_id: ULong): Response<Result<HistoryPemesanan?>>

  @POST("pesanan")
  suspend fun store(@Body body: RequestBody): Response<Result<String?>>

  //untested
  @POST("pesanan")
  suspend fun store(@Body body: RequestBodyStore): Response<Result<String?>>

  @PATCH("pesanan/{pesanan_id}/rate")
  suspend fun rate(@Path("pesanan_id") pesanan_id: ULong, @Body body: RequestBody): Response<Result<String?>>

  @GET("pesanan/showDelivery")
  suspend fun fetchDelivery(@QueryMap parameters: Map<String, String?>): Response<Result<List<DetailPemesanan>?>?>

  @POST("pesanan/{pesanan_id}/reject")
  suspend fun reject(@Path("pesanan_id") pesanan_id: ULong): Response<Result<String?>>

  @POST("pesanan/{pesanan_id}/accept")
  suspend fun accept(@Path("pesanan_id") pesanan_id: ULong): Response<Result<String?>>

  @POST("pesanan/deliver/{detail_id}")
  suspend fun deliver(@Path("detail_id") detail_id: ULong): Response<Result<String?>>

  @POST("pesanan/receive/{detail_id}")
  suspend fun receive(@Path("detail_id") detail_id: ULong): Response<Result<String?>>

  @POST("pesanan/pesanCart")
  suspend fun pesanCart(): Response<Result<String?>>
}
