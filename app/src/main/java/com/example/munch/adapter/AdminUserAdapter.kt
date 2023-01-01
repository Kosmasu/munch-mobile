package com.example.munch.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.munch.R
import com.example.munch.model.User

class AdminUserAdapter(
    private val data: List<User>
): RecyclerView.Adapter<AdminUserAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
        return ViewHolder(itemView.inflate(
            R.layout.layout_list_user, parent, false
        ))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]

        holder.tvID.text = item.users_id.toString()
        holder.tvNama.text = item.users_nama
        holder.tvEmail.text = item.users_email
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val tvID: TextView = itemView.findViewById(R.id.layout_admin_user_id)
        val tvNama: TextView = itemView.findViewById(R.id.layout_admin_user_nama)
        val tvEmail: TextView = itemView.findViewById(R.id.layout_admin_user_email)
        init {
            view.setOnClickListener {
                onClickListener?.invoke(it, adapterPosition, data[adapterPosition])
            }
        }
    }

    var onClickListener:((view: View, position: Int, user: User)->Unit)? = null
}