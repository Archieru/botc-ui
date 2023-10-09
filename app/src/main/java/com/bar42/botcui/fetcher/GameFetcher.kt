package com.bar42.botcui.fetcher

import androidx.appcompat.app.AppCompatActivity
import com.bar42.botcui.model.Game
import com.bar42.botcui.model.enums.Scenario


class GameFetcher(context: AppCompatActivity): BaseFetcher(context) {
    private val gameInterface = retrofit.create(GameInterface::class.java)
    fun getAll(callback: (List<Game>) -> Any) {
        executeCallback(callback) {
            gameInterface.getAll()
        }
    }

    fun addGame(callback: (Game) -> Any) {
        executeCallback(callback) {
            gameInterface.addGame()
        }
    }

    fun addGame(game: Game, callback: (Game) -> Any) {
        executeCallback(callback) {
            gameInterface.addGame(game)
        }
    }

    fun getGame(gameId: Int, callback: (Game) -> Any) {
        executeCallback(callback) {
            gameInterface.getGame(gameId)
        }
    }
    fun dealGame(gameId: Int, scenario: Scenario) {
        executeRequest {
            gameInterface.dealGame(gameId, scenario)
        }
    }
}