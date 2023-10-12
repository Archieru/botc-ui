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

    fun saveGame(game: Game, callback: (Game) -> Any) {
        executeCallback(callback) {
            gameInterface.saveGame(game)
        }
    }

    fun getGame(gameId: Int, callback: (Game) -> Unit) {
        executeCallback(callback) {
            gameInterface.getGame(gameId)
        }
    }

    fun dealGame(gameId: Int, scenario: Scenario, callback: () -> Unit) {
        executeCallback(callback) {
            gameInterface.dealGame(gameId, scenario)
        }
    }

    fun startGame(gameId: Int, callback: () -> Unit) {
        executeCallback(callback) {
            gameInterface.startGame(gameId)
        }
    }

    fun startDay(gameId: Int, callback: () -> Unit) {
        executeCallback(callback) {
            gameInterface.startDay(gameId)
        }
    }

    fun startNight(gameId: Int, callback: () -> Unit) {
        executeCallback(callback) {
            gameInterface.startNight(gameId)
        }
    }

    fun finishGame(gameId: Int, callback: () -> Unit) {
        executeCallback(callback) {
            gameInterface.finishGame(gameId)
        }
    }
}