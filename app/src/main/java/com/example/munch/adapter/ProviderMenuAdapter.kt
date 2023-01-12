package com.example.munch.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.munch.R
import com.example.munch.api.Retrofit
import com.example.munch.databinding.LayoutCardProviderBinding
import com.example.munch.model.Menu
import com.squareup.picasso.Picasso

class ProviderMenuAdapter(
    val context: Context,
    var menuList : ArrayList<Menu>
) : RecyclerView.Adapter<ProviderMenuAdapter.MenuHolder>() {
    private val TAG = "ProviderMenuAdapter"

    inner class MenuHolder(binding : LayoutCardProviderBinding) : RecyclerView.ViewHolder(binding.root){
        val image = binding.imageView
        val caption = binding.tvNamaMenuProvider
        init {
            binding.root.setOnClickListener {
                onClickListener?.invoke(it, adapterPosition, menuList[adapterPosition])
            }
        }
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
//        Log.d(TAG, "onBindViewHolder: menu_foto = ${menuList[position].menu_foto}")
        val url = Retrofit.hostUrl + "/storage/" + menuList[position].menu_foto
//        Log.d(TAG, "onBindViewHolder: menu_foto = $url")
        Picasso.get()
            .load(url)
            .placeholder(R.drawable.samplefood)
            .error(R.drawable.samplefood)
            .resize(230,160)
            .centerCrop()
            .into(holder.image)
        holder.caption.text = menuList[position].menu_nama
    }

    override fun getItemCount(): Int {
        return menuList.size
    }

    private var onClickListener:((view: View, position: Int, menu: Menu)->Unit)? = null
    fun onClickListener(func: (view: View, position: Int, menu: Menu) -> Unit) {
        this.onClickListener = func
    }
}