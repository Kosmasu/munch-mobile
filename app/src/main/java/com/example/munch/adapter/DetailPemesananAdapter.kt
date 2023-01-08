package com.example.munch.adapter

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.munch.databinding.LayoutListTabel3Binding
import com.example.munch.model.DetailPemesanan

class DetailPemesananAdapter(
    val context: Activity,
    val detailList : ArrayList<DetailPemesanan?>
) : RecyclerView.Adapter<DetailPemesananAdapter.DetailPemesananHolder>() {
    private val TAG = "DetailPemesananAdapter"

    inner class DetailPemesananHolder(binding: LayoutListTabel3Binding) : RecyclerView.ViewHolder(binding.root){
        val tanggal = binding.tvTabelKolom1
        val namaMenu = binding.tvTabelKolom2
        val jumlah = binding.tvTabelKolom3
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailPemesananHolder {
        val binding = LayoutListTabel3Binding.inflate(LayoutInflater.from(context),parent,false)
        return DetailPemesananHolder(binding)
    }

    override fun onBindViewHolder(holder: DetailPemesananHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder: detailPesanan = ${detailList[position]}")

        holder.tanggal.text = detailList[position]?.detail_tanggal.toString()
        holder.namaMenu.text = detailList[position]?.menu?.menu_nama.toString()
        holder.jumlah.text = detailList[position]?.detail_jumlah.toString()
    }

    override fun getItemCount(): Int {
        return detailList.size
    }
}