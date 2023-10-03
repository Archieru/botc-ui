package com.bar42.botcui.fetcher

import com.bar42.botcui.model.Game
import com.bar42.botcui.model.enums.Scenario
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface GameInterface {
    @GET("games")
    suspend fun getAll(): Response<List<Game>>

    @POST("games")
    fun addGame(@Body game: Game)

    @GET("games/{gameId}")
    fun getGame(@Path("gameId") gameId: Int): Call<Game>

    @POST("games/{gameId}/deal/{scenario}")
    fun startGame(@Path("gameId") gameId: Int, @Path("scenario") scenario: Scenario)
}
