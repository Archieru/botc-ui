package com.bar42.botcui.fetcher

import com.bar42.botcui.model.Player
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PlayerInterface {
    @GET("games/{gameId}/players")
    fun getAllPlayers(@Path("gameId") gameId: Int): Call<List<Player>>

    @GET("games/{gameId}/players/{name}")
    fun getPlayerInfo(@Path("gameId") gameId: Int, @Path("name") name: String): Call<Player>

    @POST("games/{gameId}/players/{name}")
    fun addPlayerToGame(@Path("gameId") gameId: Int, @Path("name") name: String): Call<Player>

    @GET("games/{gameId}/players/{name}/startinfo")
    fun getPlayerStartInfo(@Path("gameId") gameId: Int, @Path("name") name: String): Call<Player>
}