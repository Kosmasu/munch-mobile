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
import com.example.munch.model.HeaderCart

class CustomerCartAdapter(
    var data: List<HeaderCart>
): RecyclerView.Adapter<CustomerCartAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
        return ViewHolder(itemView.inflate(
            R.layout.layout_list_cart, parent, false
        ))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]

        holder.tvProvider.text = item.users_nama
        holder.tvTotal.text = "${item.sum_cart_total?.toRupiah()},00"
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val tvProvider: TextView = itemView.findViewById(R.id.tvCartListProvider)
        val tvTotal: TextView = itemView.findViewById(R.id.tvCartListTotal)
        val btnDetails: Button = itemView.findViewById(R.id.btnCartAction)
        init {
            btnDetails.setOnClickListener {
                onClickListener?.invoke(data[adapterPosition])
            }
        }
    }

    var onClickListener:((cart: HeaderCart)->Unit)? = null
}