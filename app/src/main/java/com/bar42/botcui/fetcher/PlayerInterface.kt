package com.bar42.botcui.fetcher

import com.bar42.botcui.model.Player
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PlayerInterface {
    @GET("games/{gameId}/players")
    suspend fun getAllPlayers(@Path("gameId") gameId: Int): Response<List<Player>>

    @GET("games/{gameId}/players/{name}")
    suspend fun getPlayerInfo(@Path("gameId") gameId: Int, @Path("name") name: String): Response<Player>

    @POST("games/{gameId}/players/{name}")
    suspend fun addPlayerToGame(@Path("gameId") gameId: Int, @Path("name") name: String): Response<Player>

    @GET("games/{gameId}/players/{name}/startinfo")
    suspend fun getPlayerStartInfo(@Path("gameId") gameId: Int, @Path("name") name: String): Response<Player>

    @POST("games/{gameId}/players/{name}/kill")
    suspend fun killPlayer(@Path("gameId") gameId: Int, @Path("name") name: String): Response<Void>

    @POST("games/{gameId}/players/{name}/resurrect")
    suspend fun resurrectPlayer(@Path("gameId") gameId: Int, @Path("name") name: String): Response<Void>
}