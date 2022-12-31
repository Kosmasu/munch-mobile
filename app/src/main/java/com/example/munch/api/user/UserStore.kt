package com.example.munch.api.user

import android.content.Context
import com.example.munch.api.Retrofit
import com.example.munch.model.Response
import com.example.munch.model.User
import okhttp3.RequestBody

class UserStore(val context: Context) : UserAPI {
  private var userAPI: UserAPI = Retrofit.getInstance(context).create(UserAPI::class.java)

  companion object {
    //https://stackoverflow.com/questions/37709918/warning-do-not-place-android-context-classes-in-static-fields-this-is-a-memory
    //yang penting pake getApplicationContext(), kata stack overflow no problem :D
    private var _instance : UserStore? = null

    fun getInstance(context: Context): UserStore {
      if (_instance == null) {
        _instance = UserStore(context.applicationContext)
      }
      return _instance!!
    }
  }

  override suspend fun fetch(body: RequestBody): Response<List<User>> {
    return userAPI.fetch(body)
  }

  override suspend fun fetch(users_id: Int): Response<User> {
    return userAPI.fetch(users_id)
  }

  override suspend fun update(users_id: Int, body: RequestBody): Response<Nothing> {
    return userAPI.update(users_id, body)
  }

  override suspend fun ban(users_id: Int): Response<Nothing> {
    return userAPI.ban(users_id)
  }

  override suspend fun unban(users_id: Int): Response<Nothing> {
    return userAPI.unban(users_id)
  }

  override suspend fun delete(users_id: Int): Response<Nothing> {
    return userAPI.delete(users_id)
  }

  override suspend fun restore(users_id: Int): Response<Nothing> {
    return userAPI.restore(users_id)
  }
}