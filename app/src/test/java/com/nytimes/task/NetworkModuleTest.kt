package com.nytimes.task

import com.nytimes.task.di.NetworkModule
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject
import retrofit2.Retrofit

class NetworkModuleTest : KoinTest {

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(NetworkModule)
    }

    @Test
    fun checkLogging() {
        val loggingInterceptor: HttpLoggingInterceptor by inject()
        assertEquals(HttpLoggingInterceptor.Level.BODY, loggingInterceptor.level)
    }

    @Test
    fun checkBaseUrl() {
        val retrofit: Retrofit by inject()
        assertEquals("http://api.nytimes.com/svc/mostpopular/v2/".trim(), retrofit.baseUrl().toUrl().toString().trim())    }
}