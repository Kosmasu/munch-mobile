package com.example.munch.api.auth

import com.example.munch.model.Response
import com.example.munch.model.User
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST

interface AuthAPI {
  @POST("register")
  suspend fun register(@Body body: RequestBody): Response<String?>

  @POST("login-api")
  suspend fun login (@Body body: RequestBody): Response<LoginResponse?>

  @GET("me")
  suspend fun me(): Response<User?>

  @GET("mystat")
  suspend fun myStat(): Response<MyStatResponse?>

  @GET("mini-me")
  suspend fun miniMe(): Response<User?>

  @PATCH("topup-api")
  suspend fun topup(): Response<String?>

  @POST("logout")
  suspend fun logout(): Response<String?>
}
