package com.example.munch.api.auth

import android.content.Context
import com.example.munch.model.Response
import com.example.munch.model.User
import com.example.munch.room.AppDatabase
import com.example.munch.room.Token
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import retrofit2.Retrofit

class AuthStore(val context: Context) : AuthAPI {
  private var token: Token? = null
  public lateinit var authAPI: AuthAPI

  override suspend fun register(body: RequestBody): Response<String?> {
    return authAPI.register(body)
  }

  override suspend fun login(body: RequestBody): Response<LoginResponse?> {
    val response = authAPI.login(body)
    if (response.data != null && response.data.access_token != null) {
      storeToken(context, Token(response.data.users_id, response.data.access_token))
    }
    com.example.munch.api.Retrofit.resetInstance(context)
    _authStore!!.authAPI = com.example.munch.api.Retrofit.getInstance(context).create(AuthAPI::class.java)
    return response
  }

  override suspend fun me(): Response<User?> {
    return authAPI.me()
  }

  override suspend fun myStat(): Response<String?> {
    return authAPI.myStat()
  }

  override suspend fun miniMe(): Response<User?> {
    return authAPI.miniMe()
  }

  override suspend fun topup(): Response<String?> {
    return authAPI.topup()
  }

  override suspend fun logout(): Response<String?> {
    removeToken(context)
    com.example.munch.api.Retrofit.resetInstance(context)
    _authStore!!.authAPI = com.example.munch.api.Retrofit.getInstance(context).create(AuthAPI::class.java)
    return authAPI.logout()
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

  companion object {
    //https://stackoverflow.com/questions/37709918/warning-do-not-place-android-context-classes-in-static-fields-this-is-a-memory
    //yang penting pake getApplicationContext(), kata stack overflow no problem :D
    private var _authStore : AuthStore? = null

    fun getInstance(context: Context): AuthStore {
      if (_authStore == null) {
        _authStore = AuthStore(context.applicationContext)
        _authStore!!.authAPI = com.example.munch.api.Retrofit.getInstance(context).create(AuthAPI::class.java)
      }
      return _authStore!!
    }
  }
}