package com.example.munch.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.munch.R
import com.example.munch.helpers.CurrencyUtils.toRupiah
import com.example.munch.model.Cart

class CustomerCartDetailsAdapter(
    val data : List<Cart>
): RecyclerView.Adapter<CustomerCartDetailsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerCartDetailsAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
        return ViewHolder(itemView.inflate(
            R.layout.layout_grid_summary, parent, false
        ))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CustomerCartDetailsAdapter.ViewHolder, position: Int) {
        val item = data[position]

        holder.tvTanggal.text = item.cart_tanggal
        holder.tvOrder.text = "${item.cart_jumlah} - ${item.cart_total?.toRupiah()},00"
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val tvTanggal: TextView = itemView.findViewById(R.id.tvSummaryTanggal)
        val tvOrder: TextView = itemView.findViewById(R.id.tvSummaryOrder)
        val btnRemove: Button = itemView.findViewById(R.id.btnRemovePesanan)
        init {
            btnRemove.setOnClickListener {
                onClickListener?.invoke(data[adapterPosition])
            }
        }
    }

    var onClickListener:((cartDetails: Cart)->Unit)? = null
}