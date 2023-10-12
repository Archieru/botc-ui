package com.bar42.botcui.fetcher

import androidx.appcompat.app.AppCompatActivity
import com.bar42.botcui.model.Player
import retrofit2.http.Path

class PlayerFetcher(context: AppCompatActivity): BaseFetcher(context) {
    private val playerInterface = retrofit.create(PlayerInterface::class.java)

    fun getAllPlayers(gameId: Int, callback: (List<Player>) -> Unit) {
        executeCallback(callback) {
            playerInterface.getAllPlayers(gameId)
        }
    }

    fun getPlayerInfo(gameId: Int, name: String, callback: (Player) -> Unit) {
        executeCallback(callback) {
            playerInterface.getPlayerInfo(gameId, name)
        }
    }

    fun addPlayerToGame(gameId: Int, name: String, callback: (Player) -> Unit = {}) {
        executeCallback(callback) {
            playerInterface.addPlayerToGame(gameId, name)
        }
    }

    fun getPlayerStartInfo(gameId: Int, name: String, callback: (Player) -> Unit) {
        executeCallback(callback) {
            playerInterface.getPlayerStartInfo(gameId, name)
        }
    }

    fun killPlayer(@Path("gameId") gameId: Int, @Path("name") name: String, callback: () -> Unit) {
        executeCallback(callback) {
            playerInterface.killPlayer(gameId, name)
        }
    }

    fun resurrectPlayer(@Path("gameId") gameId: Int, @Path("name") name: String, callback: () -> Unit) {
        executeCallback(callback) {
            playerInterface.resurrectPlayer(gameId, name)
        }
    }
}