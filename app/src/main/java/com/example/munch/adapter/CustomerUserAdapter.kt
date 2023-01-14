package com.example.munch.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.munch.R
import com.example.munch.model.User

class CustomerUserAdapter(
    var data: List<User>
): RecyclerView.Adapter<CustomerUserAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
        return ViewHolder(itemView.inflate(
            R.layout.layout_card_provider, parent, false
        ))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]

        holder.tvProvider.text = item.users_nama
        holder.tvRating.text = item.users_rating.toString()
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val tvProvider: TextView = itemView.findViewById(R.id.tvNamaMenuProvider)
        val tvRating: TextView = itemView.findViewById(R.id.tvRatingProvider)
        init {
            view.setOnClickListener {
                onClickListener?.invoke(data[adapterPosition])
            }
        }
    }

    var onClickListener:((user: User)->Unit)? = null
}