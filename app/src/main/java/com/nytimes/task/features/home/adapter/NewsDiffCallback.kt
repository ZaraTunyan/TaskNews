package com.nytimes.task.features.home.adapter

import androidx.recyclerview.widget.DiffUtil
import com.nytimes.task.network.data.NewsItem

class NewsDiffCallback(private val oldList: List<NewsItem>?, private val newList: List<NewsItem>?) :
    DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList?.size ?: 0

    override fun getNewListSize(): Int = newList?.size ?: 0

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList?.get(oldItemPosition)?.id ?: 0L == newList?.get(newItemPosition)?.id ?: 0
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList?.get(oldItemPosition) == newList?.get(newItemPosition)
    }
}