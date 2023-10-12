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
    suspend fun saveGame(@Body game: Game): Response<Game>

    @POST("games")
    suspend fun addGame(): Response<Game>

    @GET("games/{gameId}")
    suspend fun getGame(@Path("gameId") gameId: Int): Response<Game>

    @POST("games/{gameId}/deal/{scenario}")
    suspend fun dealGame(
        @Path("gameId") gameId: Int,
        @Path("scenario") scenario: Scenario
    ): Response<Void>

    @POST("games/{gameId}/start")
    suspend fun startGame(@Path("gameId") gameId: Int): Response<Void>

    @POST("games/{gameId}/day")
    suspend fun startDay(@Path("gameId") gameId: Int): Response<Void>

    @POST("games/{gameId}/night")
    suspend fun startNight(@Path("gameId") gameId: Int): Response<Void>

    @POST("games/{gameId}/finish")
    suspend fun finishGame(@Path("gameId") gameId: Int): Response<Void>
}
