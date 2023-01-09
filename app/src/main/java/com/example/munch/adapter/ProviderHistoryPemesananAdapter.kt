package com.example.munch.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.munch.databinding.LayoutListTabel3Binding
import com.example.munch.model.HistoryPemesanan

class ProviderHistoryPemesananAdapter(
    val context: Context,
    val historyPemesananList : ArrayList<HistoryPemesanan>
): RecyclerView.Adapter<ProviderHistoryPemesananAdapter.HistoryPemesananHolder>() {

    inner class HistoryPemesananHolder(binding: LayoutListTabel3Binding) :
        RecyclerView.ViewHolder(binding.root) {

        val idTextView = binding.tvTabelKolom1
        val customerTextView = binding.tvTabelKolom2
        val tanggalTextView = binding.tvTabelKolom3
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryPemesananHolder {
        val binding = LayoutListTabel3Binding.inflate(LayoutInflater.from(context),parent, false)
        return HistoryPemesananHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryPemesananHolder, position: Int) {
        holder.idTextView.text = (position+1).toString()
        holder.customerTextView.text = historyPemesananList[position].users_customer?.users_nama ?: ""
        holder.tanggalTextView.text = historyPemesananList[position].created_at
    }

    override fun getItemCount(): Int {
        return historyPemesananList.size
    }
}