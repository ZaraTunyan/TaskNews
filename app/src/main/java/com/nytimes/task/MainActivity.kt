package com.nytimes.task

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.nytimes.task.databinding.ActivityMainBinding
import com.nytimes.task.features.HomeController

class MainActivity : AppCompatActivity() {
    private lateinit var router: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )

        router = Conductor.attachRouter(this, binding.controllerContainer, savedInstanceState)
        if (!router.hasRootController()) {
            router.setRoot(RouterTransaction.with(HomeController()))
        }
    }

}