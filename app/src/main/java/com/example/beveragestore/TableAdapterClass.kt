package com.example.beveragestore

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.beveragestore.databinding.ItemLayoutBinding

class TableAdapterClass(private var tableList: List<TableDataClass>) : RecyclerView.Adapter<TableAdapterClass.TableViewHolder>() {

    var onItemClick: ((TableDataClass) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TableViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TableViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TableViewHolder, position: Int) {
        val currentItem = tableList[position]
        holder.bind(currentItem)
    }

    override fun getItemCount() = tableList.size

    fun updateList(newList: List<TableDataClass>) {
        tableList = newList
        notifyDataSetChanged()
    }

    inner class TableViewHolder(private val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(tableList[adapterPosition])
            }
        }

        fun bind(item: TableDataClass) {
            binding.title.text = item.tableName
        }
    }
}