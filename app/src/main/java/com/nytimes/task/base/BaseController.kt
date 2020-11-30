package com.nytimes.task.base

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import io.reactivex.disposables.CompositeDisposable
import org.koin.core.KoinComponent

internal const val KEY_SAVED_STATE = "saved_state"

abstract class BaseController<VM : ViewModel<SS>, VDB : ViewDataBinding, SS : SavedState> :
    ViewBindingController<VDB>,
    KoinComponent {

    constructor() : super()
    constructor(bundle: Bundle) : super(bundle)

    protected val disposables = CompositeDisposable()

    abstract val viewModel: VM

    abstract val hasOptionsMenu : Boolean

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup,
        savedViewState: Bundle?
    ): View {
        setHasOptionsMenu(hasOptionsMenu)
        val view = super.onCreateView(inflater, container, savedViewState)

        val activity = activity!!
        onRestoreViewModel(savedViewState?.getParcelable<SS>(KEY_SAVED_STATE))
        onViewCreated(activity, binding, viewModel, savedViewState)
        onViewSubscribed(activity, binding, viewModel, disposables)

        return view
    }

    protected abstract fun onViewCreated(
        activity: Activity,
        binding: VDB,
        viewModel: VM,
        savedViewState: Bundle?
    )

    protected abstract fun onViewSubscribed(
        activity: Activity,
        binding: VDB,
        viewModel: VM,
        disposables: CompositeDisposable
    )

    override fun onDetach(view: View) {
        super.onDetach(view)
        disposables.clear()
    }

    override fun onSaveViewState(view: View, outState: Bundle) {
        val savedState = viewModel.toSavedState()
        if (savedState != null) {
            outState.putParcelable(KEY_SAVED_STATE, savedState)
        }
        super.onSaveViewState(view, outState)
    }

    private fun onRestoreViewModel(savedState: SS?) {
        viewModel.toRestoreState(savedState)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onCleared()

    }

}