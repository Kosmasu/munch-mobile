package com.example.munch.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.munch.databinding.LayoutListTabel3Binding
import com.example.munch.model.DetailPemesanan

class ProviderDeliveryAdapter(
    val context: Context,
    val pesananList : ArrayList<DetailPemesanan>
) : RecyclerView.Adapter<ProviderDeliveryAdapter.DeliveryHolder>() {
    private val TAG = "ProviderDeliveryAdapter"

    inner class DeliveryHolder(binding: LayoutListTabel3Binding) : RecyclerView.ViewHolder(binding.root){
        val nomorTextView = binding.tvTabelKolom1
        val tanggalTextView = binding.tvTabelKolom2
        val alamatTextView = binding.tvTabelKolom3
        init {
            binding.root.setOnClickListener {
                onClickListener?.invoke(it, adapterPosition, pesananList[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeliveryHolder {
        val binding = LayoutListTabel3Binding.inflate(LayoutInflater.from(context),parent,false)
        return DeliveryHolder(binding)
    }

    override fun onBindViewHolder(holder: DeliveryHolder, position: Int) {
        val customer = pesananList[position].history_pemesanan?.users_customer
        Log.d(TAG, "onBindViewHolder: $customer")

        holder.nomorTextView.text = pesananList[position].menu?.menu_nama
        holder.alamatTextView.text = customer?.users_alamat.toString()
        holder.tanggalTextView.text = pesananList[position].detail_tanggal.toString()
    }

    override fun getItemCount(): Int {
        return pesananList.size
    }

    private var onClickListener:((view: View, position: Int, pemesanan: DetailPemesanan)->Unit)? = null
    fun onClickListener(func: (view: View, position: Int, pemesanan: DetailPemesanan) -> Unit) {
        this.onClickListener = func
    }
}