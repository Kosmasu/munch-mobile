package com.example.munch.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.munch.R
import com.example.munch.helpers.CurrencyUtils.toRupiah
import com.example.munch.model.HistoryPemesanan

class CustomerHistoryPemesananAdapter(
    var data: List<HistoryPemesanan>
): RecyclerView.Adapter<CustomerHistoryPemesananAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
        return ViewHolder(itemView.inflate(
            R.layout.layout_list_tabel_3, parent, false
        ))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]

        holder.tvProvider.text = item.users_provider?.users_nama
        holder.tvTotal.text = "${item.pemesanan_total?.toRupiah()},00"
        holder.tvTanggal.text = item.created_at
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val tvProvider: TextView = itemView.findViewById(R.id.tvTabelKolom1)
        val tvTotal: TextView = itemView.findViewById(R.id.tvTabelKolom2)
        val tvTanggal: TextView = itemView.findViewById(R.id.tvTabelKolom3)
        init {
            view.setOnClickListener {
                onClickListener?.invoke(it, adapterPosition, data[adapterPosition])
            }
        }
    }

    var onClickListener:((view: View, position: Int, pemesanan: HistoryPemesanan)->Unit)? = null
}