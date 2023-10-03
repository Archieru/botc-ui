package com.bar42.botcui.fetcher

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URL

open class BaseFetcher() {
    companion object {
        private val retrofit = Retrofit.Builder()
            .baseUrl(URL("https://df1c-62-228-134-33.ngrok-free.app/api/v1/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val gameInterface = retrofit.create(GameInterface::class.java)
        val playerInterface = retrofit.create(PlayerInterface::class.java)
    }
}