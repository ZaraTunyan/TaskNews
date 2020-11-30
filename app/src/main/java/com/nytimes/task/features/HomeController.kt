package com.nytimes.task.features

import android.app.Activity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.SearchView
import com.nytimes.task.R
import com.nytimes.task.base.BaseController
import com.nytimes.task.databinding.ControllerHomeBinding
import com.nytimes.task.features.adapter.NewsAdapter
import com.nytimes.task.utils.throwingSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import org.koin.core.inject
import org.koin.core.parameter.parametersOf


class HomeController : BaseController<HomeViewModel, ControllerHomeBinding, Nothing>() {

    private val adapter = NewsAdapter()

    override val hasOptionsMenu: Boolean = true

    override val viewModel by (inject<HomeViewModel> { parametersOf(activity?.getString(R.string.nyt_api_key)) })

    override fun layoutRes(): Int = R.layout.controller_home

    override fun onViewCreated(
        activity: Activity,
        binding: ControllerHomeBinding,
        viewModel: HomeViewModel,
        savedViewState: Bundle?
    ) {
        viewModel.loadNews()
        binding.apply {
            recyclerView.adapter = adapter
            refreshLayout.setOnRefreshListener {
                viewModel.loadNews()
            }
        }
    }

    override fun onViewSubscribed(
        activity: Activity,
        binding: ControllerHomeBinding,
        viewModel: HomeViewModel,
        disposables: CompositeDisposable
    ) {
        val shareObs = viewModel.getStateObservable().share()

        shareObs.filter { it is HomeViewModel.HomeState.NewsListUpdated }
            .map { it as HomeViewModel.HomeState.NewsListUpdated }
            .map {
                viewModel.calculateDiff(adapter.getItems(), it.list)
                return@map it.list
            }
            .throwingSubscribe {
                adapter.updateItems(it)
                binding.tvEmpty.visibility = if (it.isNotEmpty()) View.GONE else View.VISIBLE
            }

        shareObs.filter { it is HomeViewModel.HomeState.DiffResult }
            .map { it as HomeViewModel.HomeState.DiffResult }
            .throwingSubscribe {
                adapter.dispatchUpdates(it.diff)
            }

        shareObs.filter { it is HomeViewModel.HomeState.LoadingStatusChanges }
            .map { it as HomeViewModel.HomeState.LoadingStatusChanges }
            .throwingSubscribe {
                binding.state = it.isStarted
            }

        shareObs.filter { it is HomeViewModel.HomeState.SearchQuery }
            .map { it as HomeViewModel.HomeState.SearchQuery }
            .throwingSubscribe {
                adapter.filter.filter(it.query)
            }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
        (menu.findItem(R.id.action_search).actionView as SearchView).setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.changeSearchQuery(newText)
                return false
            }

        })
    }
}
