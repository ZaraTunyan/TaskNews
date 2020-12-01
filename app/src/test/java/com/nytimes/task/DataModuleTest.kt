package com.nytimes.task

import com.nytimes.task.di.DataModule
import com.nytimes.task.di.NetworkModule
import com.nytimes.task.network.api.ApiServiceRepository
import com.nytimes.task.utils.Event
import com.nytimes.task.utils.asEvents
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject

class DataModuleTest : KoinTest {

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(listOf(DataModule, NetworkModule))
    }

    @Test
    fun checkConnection() {
        val apiServiceRepository: ApiServiceRepository by inject()
        assertTrue(apiServiceRepository.getNews("").asEvents().blockingLast() is Event.Error)
    }


    @Test
    fun checkNewsData() {
        val apiServiceRepository: ApiServiceRepository by inject()
        assertNotNull("News Response is null" , apiServiceRepository.getNews("3DRrUziUdmp7GwJvUDm0W8bAGHRtjoSg").blockingFirst().result)
    }


}