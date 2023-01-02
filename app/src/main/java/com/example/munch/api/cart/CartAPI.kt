package com.example.munch.api.cart

import com.example.munch.model.*
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface CartAPI {
  @GET("cart")
  suspend fun fetchPaginate(@QueryMap parameters: Map<String, String>): Response<Paginate<List<Cart>>>

  @GET("cart")
  suspend fun fetchUnpaginated(@QueryMap parameters: Map<String, String>): Response<List<Cart>>

  @GET("cart/cart_id}")
  suspend fun fetch(@Path("cart_id") cart_id: ULong): Response<Cart>

  @POST("cart")
  suspend fun create(@Body body: RequestBody): Response<String?>

  @PATCH("cart/{cart_id}")
  suspend fun update(@Path("cart_id") cart_id: ULong, @Body body: RequestBody): Response<String?>

  @DELETE("cart/{cart_id}")
  suspend fun delete(@Path("cart_id") cart_id: ULong): Response<String?>

  @DELETE("cart/clear")
  suspend fun clear(): Response<String?>
}
