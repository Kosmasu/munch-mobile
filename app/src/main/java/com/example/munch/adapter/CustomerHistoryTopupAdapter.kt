package com.example.munch.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.munch.R
import com.example.munch.helpers.CurrencyUtils.toRupiah
import com.example.munch.model.HistoryTopUp

class CustomerHistoryTopupAdapter(
    var data: List<HistoryTopUp>
): RecyclerView.Adapter<CustomerHistoryTopupAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
        return ViewHolder(itemView.inflate(
            R.layout.layout_list_tabel_2, parent, false
        ))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]

        holder.tvNominal.text = "${item.topup_nominal?.toRupiah()},00"
        holder.tvTanggal.text = item.topup_tanggal.toString()

        if (item.topup_response_code == 200) {
            holder.tvNominal.setTextColor(Color.parseColor("#7CB342"))
        } else {
            holder.tvNominal.setTextColor(Color.parseColor("#FF0000"))
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val tvNominal: TextView = itemView.findViewById(R.id.tvTabelKolom2_1)
        val tvTanggal: TextView = itemView.findViewById(R.id.tvTabelKolom2_2)
    }
}