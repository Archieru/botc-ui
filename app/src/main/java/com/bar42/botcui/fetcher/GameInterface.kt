package com.bar42.botcui.fetcher

import com.bar42.botcui.model.Game
import com.bar42.botcui.model.enums.Scenario
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface GameInterface {
    @GET("games")
    suspend fun getAll(): Response<List<Game>>

    @POST("games")
    suspend fun addGame(@Body game: Game)

    @GET("games/{gameId}")
    suspend fun getGame(@Path("gameId") gameId: Int): Response<Game>

    @POST("games/{gameId}/deal/{scenario}")
    suspend fun startGame(@Path("gameId") gameId: Int, @Path("scenario") scenario: Scenario)
}
