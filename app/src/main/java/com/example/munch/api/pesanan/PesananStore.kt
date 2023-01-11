package com.example.munch.api.pesanan

import android.content.Context
import com.example.munch.api.Retrofit
import com.example.munch.model.*
import retrofit2.Response
import okhttp3.RequestBody

class PesananStore(val context: Context) : PesananAPI {
  private var pesananAPI: PesananAPI = Retrofit.getInstance(context).create(PesananAPI::class.java)

  companion object {
    //https://stackoverflow.com/questions/37709918/warning-do-not-place-android-context-classes-in-static-fields-this-is-a-memory
    //yang penting pake getApplicationContext(), kata stack overflow no problem :D
    private var _instance : PesananStore? = null

    fun getInstance(context: Context): PesananStore {
      if (_instance == null) {
        _instance = PesananStore(context.applicationContext)
      }
      return _instance!!
    }
  }

  override suspend fun fetchPaginate(parameters: Map<String, String?>): Response<Result<Paginate<List<HistoryPemesanan>>>> {
    return pesananAPI.fetchPaginate(parameters)
  }

  override suspend fun fetchUnpaginated(parameters: Map<String, String?>): Response<Result<List<HistoryPemesanan>>> {
    return pesananAPI.fetchUnpaginated(parameters)
  }

  override suspend fun fetch(pesanan_id: ULong): Response<Result<HistoryPemesanan?>> {
    return pesananAPI.fetch(pesanan_id)
  }

  override suspend fun store(body: RequestBody): Response<Result<String?>> {
    return pesananAPI.store(body)
  }

  override suspend fun rate(pesanan_id: ULong, body: RequestBody): Response<Result<String?>> {
    return pesananAPI.rate(pesanan_id, body)
  }

  override suspend fun fetchDelivery(parameters: Map<String, String?>): Response<Result<List<DetailPemesanan>>> {
    return pesananAPI.fetchDelivery(parameters)
  }

  override suspend fun reject(pesanan_id: ULong): Response<Result<String?>> {
    return pesananAPI.reject(pesanan_id)
  }

  override suspend fun accept(pesanan_id: ULong): Response<Result<String?>> {
    return pesananAPI.accept(pesanan_id)
  }

  override suspend fun deliver(detail_id: ULong): Response<Result<String?>> {
    return pesananAPI.deliver(detail_id)
  }

  override suspend fun receive(detail_id: ULong): Response<Result<String?>> {
    return pesananAPI.receive(detail_id)
  }

  override suspend fun pesanCart(): Response<Result<String?>> {
    return pesananAPI.pesanCart()
  }
}