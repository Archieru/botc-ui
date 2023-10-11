package com.bar42.botcui.fetcher

import androidx.appcompat.app.AppCompatActivity
import com.bar42.botcui.model.Player

class PlayerFetcher(context: AppCompatActivity): BaseFetcher(context) {
    private val playerInterface = retrofit.create(PlayerInterface::class.java)

    fun getAllPlayers(gameId: Int, callback: (List<Player>) -> Any) {
        executeCallback(callback) {
            playerInterface.getAllPlayers(gameId)
        }
    }

    fun getPlayerInfo(gameId: Int, name: String, callback: (Player) -> Any) {
        executeCallback(callback) {
            playerInterface.getPlayerInfo(gameId, name)
        }
    }

    fun addPlayerToGame(gameId: Int, name: String, callback: (Player) -> Any = {}) {
        executeCallback(callback) {
            playerInterface.addPlayerToGame(gameId, name)
        }
    }

    fun getPlayerStartInfo(gameId: Int, name: String, callback: (Player) -> Any) {
        executeCallback(callback) {
            playerInterface.getPlayerStartInfo(gameId, name)
        }
    }
}