package com.bar42.botcui.fetcher

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URL

open class BaseFetcher(val context: AppCompatActivity) {
    val retrofit = Retrofit.Builder()
        .baseUrl(URL("http://5.181.202.28:8080/api/v1/"))
//        .baseUrl(URL("https://sound-mink-natural.ngrok-free.app/api/v1/"))
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T>executeCallback(callback: (T) -> Any, response: suspend () -> Response<T>) {
        context.lifecycleScope.launch (Dispatchers.IO) {
            val body = response.invoke().body()!!

            withContext(Dispatchers.Main) {
                callback.invoke(body)
            }
        }
    }

    fun <T>executeCallback(callback: () -> Any, response: suspend () -> Response<T>) {
        context.lifecycleScope.launch (Dispatchers.IO) {
            response.invoke()
            withContext(Dispatchers.Main) {
                callback.invoke()
            }
        }
    }
}