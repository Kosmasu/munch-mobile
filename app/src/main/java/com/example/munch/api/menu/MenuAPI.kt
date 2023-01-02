package com.example.munch.api.menu

import com.example.munch.model.*
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface MenuAPI {
  @GET("menu")
  suspend fun fetchPaginate(@QueryMap parameters: Map<String, String>): Response<Paginate<List<Menu>>>

  @GET("menu")
  suspend fun fetchUnpaginated(@QueryMap parameters: Map<String, String>): Response<List<Menu>>

  @GET("menu/{menu_id}")
  suspend fun fetch(@Path("menu_id") menu_id: ULong): Response<Menu>

  @POST("menu")
  suspend fun create(@Body body: RequestBody): Response<String?>

  @POST("menu/{menu_id}?_method=PATCH")
  suspend fun update(@Path("menu_id") menu_id: ULong, @Body body: RequestBody): Response<String?>

  @DELETE("menu/{menu_id}")
  suspend fun delete(@Path("menu_id") menu_id: ULong): Response<String?>
}
