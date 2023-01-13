package com.example.munch.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.munch.R
import com.example.munch.helpers.CurrencyUtils.toRupiah
import com.example.munch.model.Menu

class CustomerPesanAdapter(
    var data: List<Menu>
): RecyclerView.Adapter<CustomerPesanAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
        return ViewHolder(itemView.inflate(
            R.layout.layout_list_menu_checkbox, parent, false
        ))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]

        holder.cbMenu.text = "${item.menu_nama} - ${item.menu_harga?.toRupiah()},00"
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val cbMenu: CheckBox = itemView.findViewById(R.id.cbMenu)
        init {
            view.setOnClickListener {
                onClickListener?.invoke(data[adapterPosition])
            }
        }
    }

    var onClickListener:((menu: Menu)->Unit)? = null
}