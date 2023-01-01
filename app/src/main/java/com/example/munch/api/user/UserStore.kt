package com.example.munch.api.user

import android.content.Context
import com.example.munch.api.Retrofit
import com.example.munch.model.HistoryLog
import com.example.munch.model.Paginate
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

  override fun fetchPaginate(parameters: Map<String, String>): Response<Paginate<List<User>>> {
    return userAPI.fetchPaginate(parameters)
  }

  override fun fetchUnpaginated(parameters: Map<String, String>): Response<List<User>> {
    return userAPI.fetchUnpaginated(parameters)
  }

  override suspend fun fetch(users_id: ULong): Response<User> {
    return userAPI.fetch(users_id)
  }

  override suspend fun update(users_id: ULong, body: RequestBody): Response<Nothing> {
    return userAPI.update(users_id, body)
  }

  override suspend fun ban(users_id: ULong): Response<Nothing> {
    return userAPI.ban(users_id)
  }

  override suspend fun unban(users_id: ULong): Response<Nothing> {
    return userAPI.unban(users_id)
  }

  override suspend fun delete(users_id: ULong): Response<Nothing> {
    return userAPI.delete(users_id)
  }

  override suspend fun restore(users_id: ULong): Response<Nothing> {
    return userAPI.restore(users_id)
  }
}