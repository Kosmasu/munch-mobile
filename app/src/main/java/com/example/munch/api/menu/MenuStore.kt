package com.example.munch.api.menu

import android.content.Context
import com.example.munch.api.Retrofit
import com.example.munch.model.*
import retrofit2.Response
import okhttp3.MultipartBody
import okhttp3.RequestBody

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

  override suspend fun fetchPaginate(parameters: Map<String, String?>): Response<Result<Paginate<Menu>>> {
    return menuAPI.fetchPaginate(parameters)
  }

  override suspend fun fetchUnpaginated(parameters: Map<String, String?>): Response<Result<List<Menu>>> {
    return menuAPI.fetchUnpaginated(parameters)
  }

  override suspend fun fetch(menu_id: ULong): Response<Result<Menu>> {
    return menuAPI.fetch(menu_id)
  }

  override suspend fun create(
    menu_nama: String,
    menu_foto: MultipartBody.Part,
    menu_harga: Int,
    menu_status: String,
  ): Response<Result<String?>> {
    return menuAPI.create(menu_nama, menu_foto, menu_harga, menu_status)
  }

  override suspend fun create(
    menu_nama: RequestBody,
    menu_foto: MultipartBody.Part,
    menu_harga: RequestBody,
    menu_status: RequestBody,
  ): Response<Result<String?>> {
    return menuAPI.create(menu_nama, menu_foto, menu_harga, menu_status)
  }

  override suspend fun create(
    partMap: Map<String, RequestBody>,
    photo : MultipartBody.Part
  ): Response<Result<String?>> {
    return menuAPI.create(partMap,photo)
  }

  override suspend fun update(
    menu_id: ULong,
    menu_nama: RequestBody,
    menu_foto: RequestBody,
    menu_harga: RequestBody,
    menu_status: RequestBody,
  ): Response<Result<String?>> {
    return menuAPI.update(menu_id, menu_nama, menu_foto, menu_harga, menu_status)
  }

  override suspend fun delete(menu_id: ULong): Response<Result<String?>> {
    return menuAPI.delete(menu_id)
  }
}