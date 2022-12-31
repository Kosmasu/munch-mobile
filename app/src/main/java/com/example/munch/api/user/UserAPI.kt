package com.example.munch.api.user

import com.example.munch.model.Response
import com.example.munch.model.User
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface UserAPI {
  @GET("users")
  suspend fun fetch(@Body body: RequestBody): Response<List<User>>

  @GET("users/{users_id}")
  suspend fun fetch(@Path("users_id") users_id: Int): Response<User>

  @PATCH("users/{users_id}")
  suspend fun update(@Path("users_id") users_id: Int, @Body body: RequestBody): Response<Nothing>

  @PATCH("users/banUser/{users_id}")
  suspend fun ban(@Path("users_id") users_id: Int): Response<Nothing>

  @PATCH("users/unbanUser/{users_id}")
  suspend fun unban(@Path("users_id") users_id: Int): Response<Nothing>

  @DELETE("users/{users_id}")
  suspend fun delete(@Path("users_id") users_id: Int): Response<Nothing>

  @POST("users/restore/{users_id}")
  suspend fun restore(@Path("users_id") users_id: Int): Response<Nothing>
}
