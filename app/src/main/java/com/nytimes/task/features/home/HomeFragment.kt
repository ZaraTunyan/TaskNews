package com.nytimes.task.features.home

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.nytimes.task.R
import com.nytimes.task.features.home.adapter.NewsAdapter
import com.nytimes.task.network.data.NewsItem
import com.nytimes.task.utils.navigateToNewsDetails
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.layout_home_shimmer.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val homeViewModel: HomeViewModel by viewModel()
    private lateinit var adapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = NewsAdapter(object : NewsAdapter.OnNewsClickListener {
            override fun onNewsClicked(newsItem: NewsItem) {
                navigateToNewsDetails(newsItem.id)
            }
        })
        recyclerView.adapter = adapter
        homeViewModel.news.observe(viewLifecycleOwner, Observer(::bindNewsData))
        homeViewModel.isLoading.observe(viewLifecycleOwner, Observer(::bindLoadingState))
    }

    private fun bindNewsData(news: List<NewsItem>) {
        adapter.updateItems(news)
    }

    private fun bindLoadingState(isLoading: Boolean) {
        shimmerLayout.shimmer.apply {
            if (!isLoading) {
                visibility = View.GONE
                stopShimmer()
            } else {
                visibility = View.VISIBLE
                startShimmer()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
        (menu.findItem(R.id.action_search).actionView as SearchView).setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })
    }


}