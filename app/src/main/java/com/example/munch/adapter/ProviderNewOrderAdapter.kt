package com.example.munch.adapter

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.munch.databinding.LayoutListTabel2Binding
import com.example.munch.model.HistoryPemesanan

class ProviderNewOrderAdapter(
    val context: Activity,
    val newOrder : ArrayList<HistoryPemesanan>
) : RecyclerView.Adapter<ProviderNewOrderAdapter.NewOrderHolder>() {
    private val TAG = "ProviderNewOrderAdapter"

    inner class NewOrderHolder(val binding : LayoutListTabel2Binding) : RecyclerView.ViewHolder(binding.root) {
        val nama = binding.tvTabelKolom21
        val alamat = binding.tvTabelKolom22
        init {
            binding.root.setOnClickListener {
                onClickListener?.invoke(it, adapterPosition, newOrder[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewOrderHolder {
        val binding = LayoutListTabel2Binding.inflate(LayoutInflater.from(context),parent,false)
        return NewOrderHolder(binding)
    }

    override fun onBindViewHolder(holder: NewOrderHolder, position: Int) {
        val customer = newOrder[position].users_customer
        Log.d(TAG, "onBindViewHolder: customer=$customer")

        if (customer != null){
            holder.nama.text = customer.users_nama
            holder.alamat.text = customer.users_alamat
        }
    }

    override fun getItemCount(): Int {
        return newOrder.size
    }

    private var onClickListener:((view: View, position: Int, pemesanan: HistoryPemesanan)->Unit)? = null
    fun onClickListener(func: (view: View, position: Int, pemesanan: HistoryPemesanan) -> Unit) {
        this.onClickListener = func
    }
}