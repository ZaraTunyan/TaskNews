package com.nytimes.task.base

import android.os.Parcelable
import io.reactivex.disposables.CompositeDisposable

abstract class ViewModel<SS : SavedState>  {
    protected val disposables = CompositeDisposable()

    open fun toSavedState(): SS? = null

    fun onCleared() {
        disposables.clear()
    }

    open fun toRestoreState(savedState: SS?) {}
}

interface SavedState : Parcelable