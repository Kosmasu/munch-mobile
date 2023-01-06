package com.example.munch.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.munch.R
import com.example.munch.model.HistoryMenu

class AdminHistoryMenuAdapter(
    var data: List<HistoryMenu>
): RecyclerView.Adapter<AdminHistoryMenuAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
        return ViewHolder(itemView.inflate(
            R.layout.layout_list_tabel_3, parent, false
        ))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]

        holder.tvID.text = item.history_menu_id.toString()
        holder.tvMenu.text = "${item.history_menu_action} ${item.menu_id}"
        holder.tvAction.text = item.created_at
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val tvID: TextView = itemView.findViewById(R.id.tvTabelKolom1)
        val tvMenu: TextView = itemView.findViewById(R.id.tvTabelKolom2)
        val tvAction: TextView = itemView.findViewById(R.id.tvTabelKolom3)
    }
}