package com.nytimes.task.helpers

import android.content.Context
import android.widget.Toast
import com.nytimes.task.utils.Event

class ErrorHandler(val context : Context){

    fun parseError(it: Event.Error<Throwable>) {
        Toast.makeText(context, it.value.message, Toast.LENGTH_LONG).show()
    }


}