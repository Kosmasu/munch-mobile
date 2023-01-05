package com.example.munch.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.munch.api.user.UserStore
import com.example.munch.databinding.LayoutListTabel2Binding
import com.example.munch.model.HistoryPemesanan

class ProviderNewOrderAdapter(
    val context: Activity,
    val newOrder : ArrayList<HistoryPemesanan>
) : RecyclerView.Adapter<ProviderNewOrderAdapter.NewOrderHolder>() {
    private val TAG = "ProviderNewOrderAdapter"
    private lateinit var userStore: UserStore

    class NewOrderHolder(val binding : LayoutListTabel2Binding) : RecyclerView.ViewHolder(binding.root) {
        val nama = binding.tvTabelKolom21
        val alamat = binding.tvTabelKolom22
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewOrderHolder {
        val binding = LayoutListTabel2Binding.inflate(LayoutInflater.from(context),parent,false)
        return NewOrderHolder(binding)
    }

    override fun onBindViewHolder(holder: NewOrderHolder, position: Int) {
        val customer = newOrder[position].users_customer

        if (customer != null){
            holder.nama.text = customer.users_nama
            holder.alamat.text = customer.users_alamat
        }

//        if (customer != null){
//            Retrofit.coroutine.launch {
//                try {
//                    val customer = userStore.fetch(customer.users_id).data
//
//                    context.runOnUiThread{
//                        holder.nama.text = customer.users_nama
//                        holder.alamat.text = customer.users_alamat
//                    }
//                } catch (e: Exception){
//                    context.runOnUiThread {
//                        Toast.makeText(context, "Unstable Internet", Toast.LENGTH_SHORT)
//                            .show()
//                        Log.e(TAG, "onBindViewHolder: API Server Error")
//                    }
//                }
//            }
//        }
    }

    override fun getItemCount(): Int {
        return newOrder.size
    }
}