package com.example.munch.api.user

import com.example.munch.model.Paginate
import com.example.munch.model.Result
import retrofit2.Response
import com.example.munch.model.User
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface UserAPI {
  @GET("users")
  suspend fun fetchPaginate(@QueryMap parameters: Map<String, String?>): Response<Result<Paginate<List<User>>>>

  @GET("users")
  suspend fun fetchUnpaginated(@QueryMap parameters: Map<String, String?>): Response<Result<List<User>>>

  @GET("users/{users_id}")
  suspend fun fetch(@Path("users_id") users_id: ULong): Response<Result<User>>

  @PATCH("users/{users_id}")
  suspend fun update(@Path("users_id") users_id: ULong, @Body body: RequestBody): Response<Result<Nothing>>

  @PATCH("users/banUser/{users_id}")
  suspend fun ban(@Path("users_id") users_id: ULong): Response<Result<Nothing>>

  @PATCH("users/unbanUser/{users_id}")
  suspend fun unban(@Path("users_id") users_id: ULong): Response<Result<Nothing>>

  @DELETE("users/{users_id}")
  suspend fun delete(@Path("users_id") users_id: ULong): Response<Result<Nothing>>

  @POST("users/restore/{users_id}")
  suspend fun restore(@Path("users_id") users_id: ULong): Response<Result<Nothing>>

  @PATCH("users/approveProvider/{users_id}")
  suspend fun approveProvider(@Path("users_id") users_id: ULong): Response<Result<Nothing>>
}
