package com.example.munch.api.auth

import com.example.munch.model.Result
import retrofit2.Response
import com.example.munch.model.User
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST

interface AuthAPI {
  @POST("register")
  suspend fun register(@Body body: RequestBody): Response<Result<String?>>

  @POST("login-api")
  suspend fun login (@Body body: RequestBody): Response<Result<LoginResponse?>>

  @GET("me")
  suspend fun me(): Response<Result<User?>>

  @GET("mystat")
  suspend fun myStat(): Response<Result<MyStatResponse?>>

  @GET("mini-me")
  suspend fun miniMe(): Response<Result<User?>>

  @PATCH("topup-api")
  suspend fun topup(@Body body: RequestBody): Response<Result<TopUpResponse?>>

  @POST("logout")
  suspend fun logout(): Response<Result<String?>>
}
