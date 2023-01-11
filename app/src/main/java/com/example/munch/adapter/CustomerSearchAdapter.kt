package com.example.munch.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.munch.R
import com.example.munch.helpers.CurrencyUtils.toRupiah
import com.example.munch.model.Menu

class CustomerSearchAdapter(
    var data: List<Menu>
): RecyclerView.Adapter<CustomerSearchAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
        return ViewHolder(itemView.inflate(
            R.layout.layout_card_menu_v1, parent, false
        ))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]

//        holder.ivFoto.setImageResource()
        holder.tvProvider.text = item.users_id.toString()
        holder.tvNama.text = item.menu_nama
        holder.tvHarga.text = "${item.menu_harga?.toRupiah()},00"
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val ivFoto: ImageView = itemView.findViewById(R.id.ivMenuCardPic)
        val tvProvider: TextView = itemView.findViewById(R.id.tvMenuCardProvider)
        val tvNama: TextView = itemView.findViewById(R.id.tvMenuCardNama)
        val tvHarga: TextView = itemView.findViewById(R.id.tvMenuCardHarga)
        init {
            view.setOnClickListener {
                onClickListener?.invoke(data[adapterPosition])
            }
        }
    }

    var onClickListener:((menu: Menu)->Unit)? = null
}