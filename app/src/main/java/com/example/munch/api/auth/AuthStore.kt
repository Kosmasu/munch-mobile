package com.example.munch.api.auth

import android.content.Context
import android.util.Log
import com.example.munch.api.Retrofit
import com.example.munch.model.Result
import retrofit2.Response
import com.example.munch.model.User
import com.example.munch.room.AppDatabase
import com.example.munch.room.Token
import okhttp3.RequestBody

class AuthStore(val context: Context) : AuthAPI {
  private var token: Token? = null
  private var authAPI: AuthAPI = Retrofit.getInstance(context).create(AuthAPI::class.java)

  companion object {
    //https://stackoverflow.com/questions/37709918/warning-do-not-place-android-context-classes-in-static-fields-this-is-a-memory
    //yang penting pake getApplicationContext(), kata stack overflow no problem :D
    private var _instance : AuthStore? = null

    fun getInstance(context: Context): AuthStore {
      if (_instance == null) {
        _instance = AuthStore(context.applicationContext)
      }
      return _instance!!
    }

  }
  override suspend fun register(body: RequestBody): Response<Result<String?>> {
    return authAPI.register(body)
  }

  override suspend fun login(body: RequestBody): Response<Result<LoginResponse?>> {
    val response = authAPI.login(body)
    Log.d("Login", "login: $response")
    val data = response.body()!!.data
    if (data?.access_token != null) {
      storeToken(
        context,
        Token(
          data.users_id.toInt(),
          data.access_token.toString()
        )
      )
    }
    authAPI = Retrofit.resetInstance(context).create(AuthAPI::class.java)
    return response
  }

  override suspend fun me(): Response<Result<User?>> {
    return authAPI.me()
  }

  override suspend fun myStat(): Response<Result<MyStatResponse?>> {
    return authAPI.myStat()
  }

  override suspend fun miniMe(): Response<Result<User?>> {
    return authAPI.miniMe()
  }

  override suspend fun topup(body: RequestBody): Response<Result<TopUpResponse?>> {
    return authAPI.topup(body)
  }

  override suspend fun logout(): Response<Result<String?>> {
    val response = authAPI.logout()
    removeToken(context)
    authAPI = Retrofit.resetInstance(context).create(AuthAPI::class.java)
    return response
  }

  suspend fun storeToken(context: Context, token: Token) {
    val tokenDao = AppDatabase.build(context).TokenDAO
    val tokenFetch = tokenDao.fetch(token.users_id)
    //token tidak ada
    if (tokenFetch == null)
      tokenDao.insert(token)
    //token sudah ada
    else {
      tokenDao.update(token)
    }
    this.token = token
  }

  suspend fun removeToken(context: Context) {
    val tokenDao = AppDatabase.build(context).TokenDAO
    if (token != null)
      tokenDao.delete(token!!)
    token = null
  }

  //only use getToken() after calling storeToken() and before calling removeToken()
  fun getToken(): Token? {
    return token
  }

  fun getBearer(): String {
    return "Bearer ${token!!.access_token}"
  }
}