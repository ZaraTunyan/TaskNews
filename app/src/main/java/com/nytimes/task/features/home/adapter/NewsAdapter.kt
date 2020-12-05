package com.nytimes.task.features.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.nytimes.task.R
import com.nytimes.task.databinding.ItemNewsBinding
import com.nytimes.task.network.data.NewsItem
import java.util.*
import kotlin.collections.ArrayList

class NewsAdapter(private val listener: OnNewsClickListener) :
    RecyclerView.Adapter<NewsViewHolder>(), Filterable {
    private var items = listOf<NewsItem>()
    private var filterItems = listOf<NewsItem>()

    interface OnNewsClickListener {
        fun onNewsClicked(newsItem: NewsItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder =
        NewsViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.item_news, parent, false
            ),
            listener
        )

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) =
        holder.bind(filterItems[position])

    override fun getItemCount(): Int = filterItems.size

    fun updateItems(list: List<NewsItem>) {
        items = list
        filterItems = list
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                filterItems = if (charSearch.isEmpty()) {
                    items
                } else {
                    val resultList = ArrayList<NewsItem>()
                    for (row in items) {
                        if ((row.title?:"").toLowerCase(Locale.ROOT).contains(charSearch.toLowerCase(Locale.ROOT))) {
                            resultList.add(row)
                        }
                    }
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = filterItems
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filterItems = results?.values as List<NewsItem>
                notifyDataSetChanged()
            }

        }
    }
}

class NewsViewHolder(
    private val binding: ItemNewsBinding,
    listener: NewsAdapter.OnNewsClickListener
) : RecyclerView.ViewHolder(binding.root) {

    init {
        itemView.setOnClickListener { binding.news?.let { listener.onNewsClicked(it) } }
    }

    fun bind(newsItem: NewsItem) {
        binding.news = newsItem
    }

}
