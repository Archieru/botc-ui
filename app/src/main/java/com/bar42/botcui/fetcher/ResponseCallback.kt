package com.bar42.botcui.fetcher

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResponseCallback<T>(val onSuccess: (response: Response<T>) -> Any) : Callback<T> {
    override fun onResponse(call: Call<T>, response: Response<T>) {
        onSuccess(response)
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        t.printStackTrace()
    }
}