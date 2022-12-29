package com.example.munch.api

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
//import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

abstract class Retrofit {
  companion object {
    @get:Synchronized
    private var _instance: Retrofit? = null
    val coroutine = CoroutineScope(Dispatchers.IO)

    fun getInstance() : Retrofit {
//      val interceptor : HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
//        level = HttpLoggingInterceptor.Level.BODY
//      }

//      val client : OkHttpClient = OkHttpClient.Builder().apply {
//        addInterceptor(interceptor)
//      }.build()

      if(_instance == null){
        _instance = Retrofit.Builder().baseUrl("http://10.0.2.2:8000/api/")
          .addConverterFactory(GsonConverterFactory.create())
//          .client(client)
          .build()
      }

      return _instance!!
    }
  }
}