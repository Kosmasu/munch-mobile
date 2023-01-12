package com.example.munch.api.history

import android.content.Context
import com.example.munch.api.Retrofit
import com.example.munch.model.*
import retrofit2.Response

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

  override suspend fun logPaginate(parameters: Map<String, String?>): Response<Result<Paginate<HistoryLog>>> {
    return historyAPI.logPaginate(parameters)
  }

  override suspend fun logUnpaginated(parameters: Map<String, String?>): Response<Result<List<HistoryLog>>> {
    return historyAPI.logUnpaginated(parameters)
  }

  override suspend fun menuPaginate(parameters: Map<String, String?>): Response<Result<Paginate<HistoryMenu>>> {
    return historyAPI.menuPaginate(parameters)
  }

  override suspend fun menuUnpaginated(parameters: Map<String, String?>): Response<Result<List<HistoryMenu>>> {
    return historyAPI.menuUnpaginated(parameters)
  }

  override suspend fun topUpPaginate(parameters: Map<String, String?>): Response<Result<Paginate<HistoryTopUp>>> {
    return historyAPI.topUpPaginate(parameters)
  }

  override suspend fun topUpUnpaginated(parameters: Map<String, String?>): Response<Result<List<HistoryTopUp>>> {
    return historyAPI.topUpUnpaginated(parameters)
  }
}