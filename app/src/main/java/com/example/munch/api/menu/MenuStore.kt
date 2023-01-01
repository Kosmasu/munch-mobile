package com.example.munch.api.menu

import android.content.Context
import com.example.munch.api.Retrofit
import com.example.munch.model.*
import okhttp3.RequestBody
import retrofit2.http.Path

class MenuStore(val context: Context) : MenuAPI {
  private var menuAPI: MenuAPI = Retrofit.getInstance(context).create(MenuAPI::class.java)

  companion object {
    //https://stackoverflow.com/questions/37709918/warning-do-not-place-android-context-classes-in-static-fields-this-is-a-memory
    //yang penting pake getApplicationContext(), kata stack overflow no problem :D
    private var _instance : MenuStore? = null

    fun getInstance(context: Context): MenuStore {
      if (_instance == null) {
        _instance = MenuStore(context.applicationContext)
      }
      return _instance!!
    }
  }

  override fun fetchPaginate(parameters: Map<String, String>): Response<Paginate<List<Menu>>> {
    return menuAPI.fetchPaginate(parameters)
  }

  override fun fetchUnpaginated(parameters: Map<String, String>): Response<List<Menu>> {
    return menuAPI.fetchUnpaginated(parameters)
  }

  override suspend fun fetch(menu_id: ULong): Response<Menu> {
    return menuAPI.fetch(menu_id)
  }

  override suspend fun create(body: RequestBody): Response<String?> {
    return menuAPI.create(body)
  }

  override suspend fun update(menu_id: ULong, body: RequestBody): Response<String?> {
    return menuAPI.update(menu_id, body)
  }

  override suspend fun delete(menu_id: ULong): Response<String?> {
    return menuAPI.delete(menu_id)
  }
}