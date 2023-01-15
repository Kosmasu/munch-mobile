package com.example.munch.adapter

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.munch.api.Retrofit
import com.example.munch.api.menu.MenuStore
import com.example.munch.api.pesanan.RequestBodyDetailMenu
import com.example.munch.databinding.LayoutGridSummaryBinding
import com.example.munch.model.Menu
import kotlinx.coroutines.launch

class CustomerSummaryAdapter(
    val context: Activity,
    val summaryList : ArrayList<RequestBodyDetailMenu>
) : RecyclerView.Adapter<CustomerSummaryAdapter.SummaryHolder>() {
    private val TAG = "CustomerSummaryAdapter"
    private lateinit var menuStore: MenuStore

    inner class SummaryHolder(binding: LayoutGridSummaryBinding) : RecyclerView.ViewHolder(binding.root) {
        val removeBtn = binding.btnRemovePesanan
        val tanggal = binding.tvSummaryTanggal
        val order = binding.tvSummaryOrder
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SummaryHolder {
        val binding = LayoutGridSummaryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        menuStore = MenuStore.getInstance(parent.context)
        return SummaryHolder(binding)
    }

    override fun onBindViewHolder(holder: SummaryHolder, position: Int) {
        val menuId = summaryList[position].menu_id

        holder.removeBtn.setOnClickListener {
            summaryList.removeAt(position)
            notifyItemRemoved(position)
        }

        var menu : Menu
        Retrofit.coroutine.launch {
            try {
                val resp = menuStore.fetch(menuId)
                menu = resp.body()?.data!!

                holder.order.text = menu.menu_nama
                holder.tanggal.text = summaryList[position].detail_tanggal
            } catch (e: Exception) {
                Log.e(TAG, "onViewCreated: API Server error", e)
                context.runOnUiThread {
                    Toast.makeText(context, "Server error", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return summaryList.size
    }
}