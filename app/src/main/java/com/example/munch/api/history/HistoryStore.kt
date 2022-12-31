package com.example.munch.api.history

import android.content.Context
import com.example.munch.api.Retrofit
import com.example.munch.model.*
import okhttp3.RequestBody

class HistoryStore(val context: Context) : HistoryAPI {
  private var historyAPI: HistoryAPI = Retrofit.getInstance(context).create(HistoryAPI::class.java)

  companion object {
    //https://stackoverflow.com/questions/37709918/warning-do-not-place-android-context-classes-in-static-fields-this-is-a-memory
    //yang penting pake getApplicationContext(), kata stack overflow no problem :D
    private var _instance : HistoryStore? = null

    fun getInstance(context: Context): HistoryStore {
      if (_instance == null) {
        _instance = HistoryStore(context.applicationContext)
      }
      return _instance!!
    }
  }

  override fun logPaginate(parameters: Map<String, String>): Response<Paginate<List<HistoryLog>>> {
    return historyAPI.logPaginate(parameters)
  }

  override fun logUnpaginated(parameters: Map<String, String>): Response<List<HistoryLog>> {
    return historyAPI.logUnpaginated(parameters)
  }

  override fun menuPaginate(parameters: Map<String, String>): Response<Paginate<List<HistoryMenu>>> {
    return historyAPI.menuPaginate(parameters)
  }

  override fun menuUnpaginated(parameters: Map<String, String>): Response<List<HistoryMenu>> {
    return historyAPI.menuUnpaginated(parameters)
  }

  override fun topUpPaginate(parameters: Map<String, String>): Response<Paginate<List<HistoryPemesanan>>> {
    return historyAPI.topUpPaginate(parameters)
  }

  override fun topUpUnpaginated(parameters: Map<String, String>): Response<List<HistoryPemesanan>> {
    return historyAPI.topUpUnpaginated(parameters)
  }
}