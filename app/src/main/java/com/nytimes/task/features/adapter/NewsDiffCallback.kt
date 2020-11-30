package com.nytimes.task.features.adapter

import androidx.recyclerview.widget.DiffUtil
import com.nytimes.task.network.data.NewsItem

class NewsDiffCallback(private val oldList : List<NewsItem> , private val newList: List<NewsItem>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
       return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}