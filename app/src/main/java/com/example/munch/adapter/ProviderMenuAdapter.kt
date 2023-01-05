package com.example.munch.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.munch.databinding.LayoutCardProviderBinding
import com.example.munch.model.Menu
import com.squareup.picasso.Picasso

class ProviderMenuAdapter(
    val context: Context,
    val menuList : ArrayList<Menu>
) : RecyclerView.Adapter<ProviderMenuAdapter.MenuHolder>() {
    private val TAG = "ProviderMenuAdapter"

    inner class MenuHolder(binding : LayoutCardProviderBinding) : RecyclerView.ViewHolder(binding.root){
        val image = binding.imageView
        val caption = binding.tvNamaProvider
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuHolder {
        return MenuHolder(
            LayoutCardProviderBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MenuHolder, position: Int) {
//        holder.image.setImageURI(menuList[position].menu_foto)
        Log.d(TAG, "onBindViewHolder: menu_foto = ${menuList[position].menu_foto}")

//        val url =
        Picasso.get()
            .load(menuList[position].menu_foto)
            .resize(50,50)
//            .placeholder(R.drawable.user_placeholder)
//            .error(R.drawable.user_placeholder_error)
            .into(holder.image)
        holder.caption.text = menuList[position].menu_nama
    }

    override fun getItemCount(): Int {
        return menuList.size
    }
}