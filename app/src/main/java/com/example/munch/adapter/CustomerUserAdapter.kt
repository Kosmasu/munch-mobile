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
import com.example.munch.model.User
import com.squareup.picasso.Picasso

class CustomerUserAdapter(
    var data: List<User>
): RecyclerView.Adapter<CustomerUserAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
        return ViewHolder(itemView.inflate(
            R.layout.layout_card_top_catering, parent, false
        ))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]

        holder.tvProvider.text = item.users_nama
        holder.tvRating.text = item.users_rating.toString()
        if (item.users_rating == null) {
            holder.tvRating.text = "0.00"
        }

        val url = Retrofit.hostUrl + "/storage/" + item.users_photo
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
        val ivProvider: ImageView = itemView.findViewById(R.id.iv_topCatering)
        val tvProvider: TextView = itemView.findViewById(R.id.tvNama_topCatering)
        val tvRating: TextView = itemView.findViewById(R.id.tvRating_topCatering)
        init {
            view.setOnClickListener {
                onClickListener?.invoke(data[adapterPosition])
            }
        }
    }

    var onClickListener:((user: User)->Unit)? = null
}