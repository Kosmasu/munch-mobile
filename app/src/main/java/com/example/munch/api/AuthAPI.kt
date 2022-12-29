package com.example.munch.api

import com.example.munch.model.User
import okhttp3.FormBody
import okhttp3.RequestBody
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthAPI {
  @POST("register")
  suspend fun register(@Body body: RequestBody): Response
  @POST("login")
  suspend fun login (@Body body: RequestBody): ResponseBody
  @POST("login")
  suspend fun login2 (@Body body: RequestBody): com.example.munch.api.Response<User>
  @GET("me")
  suspend fun getLoggedIn(): ResponseBody
  @GET("mystat")
  suspend fun getStatus(): Response
  @GET("mini-me")
  suspend fun getMiniMe(): Response
}