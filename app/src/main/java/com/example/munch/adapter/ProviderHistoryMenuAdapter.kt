package com.example.munch.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.munch.databinding.LayoutListTabel3Binding
import com.example.munch.model.HistoryMenu

class ProviderHistoryMenuAdapter(
    val context: Context,
    val historyMenuList : ArrayList<HistoryMenu>
): RecyclerView.Adapter<ProviderHistoryMenuAdapter.HistoryMenuHolder>() {

    inner class HistoryMenuHolder(binding: LayoutListTabel3Binding) :
        RecyclerView.ViewHolder(binding.root) {

        val idTextView = binding.tvTabelKolom1
        val customerTextView = binding.tvTabelKolom2
        val tanggalTextView = binding.tvTabelKolom3
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryMenuHolder {
        val binding = LayoutListTabel3Binding.inflate(LayoutInflater.from(context),parent, false)
        return HistoryMenuHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryMenuHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return historyMenuList.size
    }
}