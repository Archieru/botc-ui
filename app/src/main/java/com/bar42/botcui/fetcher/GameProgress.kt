package com.bar42.botcui.fetcher

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.bar42.botcui.DealedGameActivity
import com.bar42.botcui.EvilFirstNightActivity
import com.bar42.botcui.PlayerGatheringActivity
import com.bar42.botcui.model.Game
import com.bar42.botcui.model.enums.GameStatus
import com.bar42.botcui.model.enums.Scenario

class GameProgress {
    private val gameFetcher: GameFetcher
    private val gameId: Int
    private val gameStatus: GameStatus
    private val context: AppCompatActivity

    constructor(game: Game, context: AppCompatActivity) {
        this.gameFetcher = GameFetcher(context)
        this.gameId = game.id!!
        this.gameStatus = game.status
        this.context = context
    }

    constructor(gameId: Int, gameStatus: GameStatus, context: AppCompatActivity) {
        gameFetcher = GameFetcher(context)
        this.gameId = gameId
        this.gameStatus = gameStatus
        this.context = context
    }

    fun openActivity(gameStatus: GameStatus) {
        when (gameStatus) {
            GameStatus.CREATED -> switchTo(PlayerGatheringActivity::class.java)
            GameStatus.PLAYERS_READY -> switchTo(DealedGameActivity::class.java)
            GameStatus.DEALED -> switchTo(DealedGameActivity::class.java)
            GameStatus.FIRST_NIGHT -> switchTo(EvilFirstNightActivity::class.java)
            GameStatus.DAY -> TODO("proceedGame GameStatus.DAY")
            GameStatus.NIGHT -> TODO("proceedGame GameStatus.NIGHT")
            GameStatus.FINISHED -> return
        }
    }

    fun proceedGame() {
        when (gameStatus) {
            GameStatus.CREATED -> dealGame()
            GameStatus.PLAYERS_READY -> dealGame()
            GameStatus.DEALED -> switchTo(EvilFirstNightActivity::class.java)
            GameStatus.FIRST_NIGHT -> TODO("proceedGame GameStatus.DAY")
            GameStatus.DAY -> TODO("proceedGame GameStatus.DAY")
            GameStatus.NIGHT -> TODO("proceedGame GameStatus.NIGHT")
            GameStatus.FINISHED -> return
        }
    }

    private fun <T> switchTo(clazz: Class<T>) where T: AppCompatActivity {
        val intent = Intent(context, clazz)
        intent.putExtra("gameId", gameId)
        context.startActivity(intent)
    }

    private fun dealGame() {
        GameFetcher(context).dealGame(gameId, Scenario.TroubleBrewing) {
            switchTo(DealedGameActivity::class.java)
        }
    }
}