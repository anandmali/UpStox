package com.anandmali.upstox.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anandmali.upstox.databinding.StocksListItemBinding
import com.anandmali.upstox.remote.data.UiStockModel

class StockListAdapter : ListAdapter<UiStockModel, ViewHolder>(CustomDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: StocksListItemBinding = StocksListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class ViewHolder(private val listItemBinding: StocksListItemBinding) :
    RecyclerView.ViewHolder(listItemBinding.root) {
    fun bind(uiStockModel: UiStockModel) {
        listItemBinding.stocks = uiStockModel
        listItemBinding.executePendingBindings()
    }
}

class CustomDiffUtil : DiffUtil.ItemCallback<UiStockModel>() {
    override fun areItemsTheSame(oldItem: UiStockModel, newItem: UiStockModel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: UiStockModel,
        newItem: UiStockModel
    ): Boolean {
        return oldItem.name == newItem.name
    }

}