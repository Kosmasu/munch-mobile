package com.example.munch.api.cart

import android.content.Context
import com.example.munch.api.Retrofit
import com.example.munch.model.*
import okhttp3.RequestBody

class CartStore(val context: Context) : CartAPI {
  private var cartAPI: CartAPI = Retrofit.getInstance(context).create(CartAPI::class.java)

  companion object {
    //https://stackoverflow.com/questions/37709918/warning-do-not-place-android-context-classes-in-static-fields-this-is-a-memory
    //yang penting pake getApplicationContext(), kata stack overflow no problem :D
    private var _instance : CartStore? = null

    fun getInstance(context: Context): CartStore {
      if (_instance == null) {
        _instance = CartStore(context.applicationContext)
      }
      return _instance!!
    }
  }

  override suspend fun fetchPaginate(parameters: Map<String, String>): Response<Paginate<List<Cart>>> {
    return cartAPI.fetchPaginate(parameters)
  }

  override suspend fun fetchUnpaginated(parameters: Map<String, String>): Response<List<Cart>> {
    return cartAPI.fetchUnpaginated(parameters)
  }

  override suspend fun fetch(cart_id: ULong): Response<Cart> {
    return cartAPI.fetch(cart_id)
  }

  override suspend fun create(body: RequestBody): Response<String?> {
    return cartAPI.create(body)
  }

  override suspend fun update(cart_id: ULong, body: RequestBody): Response<String?> {
    return cartAPI.update(cart_id, body)
  }

  override suspend fun delete(cart_id: ULong): Response<String?> {
    return cartAPI.delete(cart_id)
  }

  override suspend fun clear(): Response<String?> {
    return cartAPI.clear()
  }
}