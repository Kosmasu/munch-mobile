package com.example.munch.api

import android.content.Context
import com.example.munch.api.auth.AuthStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

//import retrofit2.converter.scalars.ScalarsConverterFactory

abstract class Retrofit {
  companion object {
    @get:Synchronized
    private var _instance: Retrofit? = null
    val coroutine = CoroutineScope(Dispatchers.IO)
    const val hostUrl: String = "http://10.0.2.2:8000"
    const val baseUrl: String = "$hostUrl/api/"

    fun getInstance(context: Context) : Retrofit {
      if(_instance == null){
        val interceptor = object : Interceptor {
          override fun intercept(chain: Interceptor.Chain): Response {
            val authStore = AuthStore.getInstance(context)
            val requestBuilder = chain.request().newBuilder().apply {
              header("Accept", "application/json")
              if (authStore.getToken() != null) {
                header("Authorization", authStore.getBearer())
              }
            }
            val request = requestBuilder.build()
            println("LOGGING INTERCEPTOR=================")
            println("Request URL = ${request.url()}")
            println("Request Header = ${request.headers()}")
            println("Request Body = ${request.body().toString()}")
            println("LOGGING INTERCEPTOR=================")
            return chain.proceed(request)
          }
        }

        val responseInterceptor = object: Interceptor {
          override fun intercept(chain: Interceptor.Chain): Response {
            val response = chain.proceed(chain.request())
            if (!response.isSuccessful) {
              println("RESPONSE INTERCEPTOR")
//              throw HttpException(response)
              throw IOException(response.message())
            }
            return response
          }
        }

        val client : OkHttpClient = OkHttpClient.Builder().apply {
          addInterceptor(interceptor)
          addInterceptor(responseInterceptor)
        }.build()

        _instance = Retrofit.Builder().baseUrl(baseUrl)
//          .addConverterFactory(ScalarsConverterFactory.create())
          .addConverterFactory(GsonConverterFactory.create())
          .client(client)
          .build()
      }
      return _instance!!
    }

    fun resetInstance(context: Context): Retrofit {
      _instance = null
      return getInstance(context)
    }
  }
}