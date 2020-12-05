package com.nytimes.task.helpers

import android.content.Context
import android.widget.Toast

class ErrorHandler(val context: Context){

    fun parseError(it: Throwable) {
        Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
    }


}