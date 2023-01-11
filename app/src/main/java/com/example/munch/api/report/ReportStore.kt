package com.example.munch.api.report

import android.content.Context
import com.example.munch.api.Retrofit
import com.example.munch.model.*
import retrofit2.Response

class ReportStore(val context: Context) : ReportAPI {
  private var reportAPI: ReportAPI = Retrofit.getInstance(context).create(ReportAPI::class.java)

  companion object {
    //https://stackoverflow.com/questions/37709918/warning-do-not-place-android-context-classes-in-static-fields-this-is-a-memory
    //yang penting pake getApplicationContext(), kata stack overflow no problem :D
    private var _instance : ReportStore? = null

    fun getInstance(context: Context): ReportStore {
      if (_instance == null) {
        _instance = ReportStore(context.applicationContext)
      }
      return _instance!!
    }
  }

  override suspend fun penjualan(): Response<Result<String?>> {
    return reportAPI.penjualan()
  }
}