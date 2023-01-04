package com.example.munch.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.munch.R
import com.example.munch.model.HistoryLog

class AdminHistoryLogAdapter(
    var data: List<HistoryLog>
): RecyclerView.Adapter<AdminHistoryLogAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
        return ViewHolder(itemView.inflate(
            R.layout.layout_list_tabel_3, parent, false
        ))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]

        holder.tvID.text = item.log_id.toString()
        holder.tvLog.text = item.log_title.toString()
        holder.tvTanggal.text = item.log_timestamp.toString()
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val tvID: TextView = itemView.findViewById(R.id.tvTabelKolom1)
        val tvLog: TextView = itemView.findViewById(R.id.tvTabelKolom2)
        val tvTanggal: TextView = itemView.findViewById(R.id.tvTabelKolom3)
    }
}