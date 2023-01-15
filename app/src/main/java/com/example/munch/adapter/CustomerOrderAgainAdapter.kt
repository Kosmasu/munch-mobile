package com.example.munch.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.munch.R
import com.example.munch.api.Retrofit
import com.example.munch.helpers.CurrencyUtils.toRupiah
import com.example.munch.model.HistoryPemesanan
import com.squareup.picasso.Picasso

class CustomerOrderAgainAdapter(
    var data: List<HistoryPemesanan>
): RecyclerView.Adapter<CustomerOrderAgainAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
        return ViewHolder(itemView.inflate(
            R.layout.layout_grid_orderagain_v1, parent, false
        ))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]

        holder.tvProvider.text = item.users_provider?.users_nama
        holder.tvTanggal.text = item.updated_at
        holder.tvHarga.text = "${item.pemesanan_jumlah} - ${item.pemesanan_total?.toRupiah()},00"

        val url = Retrofit.hostUrl + "/storage/" + item.users_provider?.users_photo
        Picasso.get()
            .load(url)
            .placeholder(R.drawable.samplefood)
            .error(R.drawable.samplefood)
            .resize(200, 160)
            .centerCrop()
            .into(holder.ivProvider)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val ivProvider: ImageView = itemView.findViewById(R.id.ivOrderAgain)
        val tvProvider: TextView = itemView.findViewById(R.id.tv_orderAgain_Provider)
        val tvTanggal: TextView = itemView.findViewById(R.id.tv_orderAgain_Tanggal)
        val tvHarga: TextView = itemView.findViewById(R.id.tv_orderAgain_Harga)
        init {
            view.setOnClickListener {
                onClickListener?.invoke(data[adapterPosition])
            }
        }
    }

    var onClickListener:((pemesanan: HistoryPemesanan)->Unit)? = null
}