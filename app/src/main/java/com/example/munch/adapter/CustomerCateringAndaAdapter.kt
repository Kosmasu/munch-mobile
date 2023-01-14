package com.example.munch.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.munch.R
import com.example.munch.model.DetailPemesanan

class CustomerCateringAndaAdapter(
    var data : ArrayList<DetailPemesanan?>
): RecyclerView.Adapter<CustomerCateringAndaAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerCateringAndaAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
        return ViewHolder(itemView.inflate(
            R.layout.layout_card_catering_anda, parent, false
        ))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]

//        holder.ivMenu
        holder.tvNama.text = "${item?.menu?.menu_nama} - ${item?.detail_jumlah}"
        holder.tvTanggal.text = item?.detail_tanggal
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val ivMenu: ImageView = itemView.findViewById(R.id.iv_cateringAnda)
        val tvNama: TextView = itemView.findViewById(R.id.tvNamaMenu_cateringAnda)
        val tvTanggal: TextView = itemView.findViewById(R.id.tvTanggal_cateringAnda)
        init {
            view.setOnClickListener {
                onClickListener?.invoke(it, data[adapterPosition]!!)
            }
        }
    }

    var onClickListener:((view: View, detail: DetailPemesanan)->Unit)? = null
}