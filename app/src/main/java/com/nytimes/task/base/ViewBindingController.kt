package com.nytimes.task.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.changehandler.HorizontalChangeHandler

fun horizontalTransaction(controller: Controller) = RouterTransaction.with(controller)
    .pushChangeHandler(HorizontalChangeHandler())
    .popChangeHandler(HorizontalChangeHandler())

abstract class ViewBindingController<VDB : ViewDataBinding> : Controller {
    constructor() : super()
    constructor(bundle: Bundle) : super(bundle)

    protected lateinit var binding: VDB

    protected abstract fun layoutRes(): Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup,
        savedViewState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(LayoutInflater.from(activity), layoutRes(), container, true)
        onViewCreated(binding, savedViewState)
        return binding.root
    }

    protected open fun onViewCreated(binding: ViewDataBinding, savedViewState: Bundle?) {
        onViewBindingCreated(binding, savedViewState)
    }

    protected open fun onViewBindingCreated(binding: ViewDataBinding, savedViewState: Bundle?) {}

}