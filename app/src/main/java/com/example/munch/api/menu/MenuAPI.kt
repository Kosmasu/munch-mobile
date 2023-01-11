package com.example.munch.api.menu

import com.example.munch.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface MenuAPI {
  @GET("menu")
  suspend fun fetchPaginate(@QueryMap parameters: Map<String, String?>): Response<Paginate<List<Menu>>>

  @GET("menu")
  suspend fun fetchUnpaginated(@QueryMap parameters: Map<String, String?>): Response<List<Menu>>

  @GET("menu/{menu_id}")
  suspend fun fetch(@Path("menu_id") menu_id: ULong): Response<Menu>

  @Multipart
  @POST("menu")
  suspend fun create(
    @Part("menu_nama") menu_nama: RequestBody,
    @Part menu_foto: MultipartBody.Part,
    @Part("menu_harga") menu_harga: RequestBody,
    @Part("menu_status") menu_status: RequestBody,
  ): Response<String?>

  @Multipart
  @POST("menu")
  suspend fun create(
    @Part("menu_nama") menu_nama: String,
    @Part menu_foto: MultipartBody.Part,
    @Part("menu_harga") menu_harga: Int,
    @Part("menu_status") menu_status: String,
  ): Response<String?>

  @Multipart
  @POST("menu")
  suspend fun create(
    @PartMap partMap: Map<String, @JvmSuppressWildcards RequestBody>,
    @Part photo: MultipartBody.Part
  ): Response<String?>

  @Multipart
  @PATCH("menu/{menu_id}")
  suspend fun update(
    @Path("menu_id") menu_id: ULong,
    @Part menu_nama: RequestBody,
    @Part menu_foto: RequestBody,
    @Part menu_harga: RequestBody,
    @Part menu_status: RequestBody,
  ): Response<String?>

  @DELETE("menu/{menu_id}")
  suspend fun delete(@Path("menu_id") menu_id: ULong): Response<String?>
}
