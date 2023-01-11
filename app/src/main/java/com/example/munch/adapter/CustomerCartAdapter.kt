package com.example.munch.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.munch.R
import com.example.munch.model.Cart

class CustomerCartAdapter(
    var data: List<Cart>
): RecyclerView.Adapter<CustomerCartAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
        return ViewHolder(itemView.inflate(
            R.layout.layout_grid_summary, parent, false
        ))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]

//        holder.btnRemove
//        holder.tvSummary
//        holder.tvOrder.text = item.menu_id
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val btnRemove: Button = itemView.findViewById(R.id.btnRemovePesanan)
        val tvSummary: TextView = itemView.findViewById(R.id.tvSummaryTanggal)
        val tvOrder: TextView = itemView.findViewById(R.id.tvSummaryOrder)
    }
}