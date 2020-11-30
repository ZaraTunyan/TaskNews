package com.nytimes.task.features

import androidx.recyclerview.widget.DiffUtil
import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.PublishRelay
import com.nytimes.task.base.ViewModel
import com.nytimes.task.features.adapter.NewsDiffCallback
import com.nytimes.task.helpers.ErrorHandler
import com.nytimes.task.network.api.ApiServiceRepository
import com.nytimes.task.network.data.BaseResponse
import com.nytimes.task.network.data.NewsItem
import com.nytimes.task.utils.Event
import com.nytimes.task.utils.asEvents
import com.nytimes.task.utils.throwingSubscribe
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

class HomeViewModel(
    private val apiServiceRepository: ApiServiceRepository,
    private val errorHandler: ErrorHandler,
    private val key: String
) : ViewModel<Nothing>() {

    private val stateRelay = BehaviorRelay.create<HomeState>()
    private val searchRelay = PublishRelay.create<String>()

    init {
        disposables += searchRelay
            .debounce(200, TimeUnit.MICROSECONDS)
            .distinctUntilChanged()
            .throwingSubscribe { stateRelay.accept(HomeState.SearchQuery(it)) }
    }

    fun loadNews() {
        disposables += apiServiceRepository.getNews(key)
            .subscribeOn(Schedulers.newThread())
            .asEvents()
            .throwingSubscribe {
                when (it) {
                    is Event.Started -> {
                        stateRelay.accept(HomeState.LoadingStatusChanges(true))
                    }
                    is Event.Success -> {
                        stateRelay.accept(HomeState.LoadingStatusChanges(false))
                        stateRelay.accept(HomeState.NewsListUpdated(it.value.result))
                    }
                    is Event.Error -> {
                        stateRelay.accept(HomeState.LoadingStatusChanges(false))
                        errorHandler.parseError(it)
                    }
                }
            }
    }

    fun calculateDiff(oldItems: List<NewsItem>, newItems: List<NewsItem>) {
       stateRelay.accept(HomeState.DiffResult(DiffUtil.calculateDiff(NewsDiffCallback(oldItems, newItems))))
    }

    fun changeSearchQuery(query: String){
        searchRelay.accept(query)
    }

    fun getStateObservable() : Observable<HomeState> = stateRelay.observeOn(AndroidSchedulers.mainThread())

    sealed class HomeState {
        data class SearchQuery(val query : String) : HomeState()
        data class LoadingStatusChanges(val isStarted : Boolean) : HomeState()
        data class NewsListUpdated(val list : List<NewsItem>) : HomeState()
        data class DiffResult(val diff: DiffUtil.DiffResult) : HomeState()
    }
}