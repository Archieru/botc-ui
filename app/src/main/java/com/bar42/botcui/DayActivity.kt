package com.bar42.botcui

import android.os.Bundle
import com.bar42.botcui.fetcher.GameProgress
import com.bar42.botcui.model.enums.GameStatus

class DayActivity : BaseGameActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.contentText.text = "DayActivity"
        binding.progressGame.setText(R.string.button_start_night)
        binding.progressGame.setOnClickListener {
            gameFetcher.startNight(gameId) {
                GameProgress(gameId, GameStatus.DAY, this).proceedGame()
            }
        }
        binding.mainAction.setText(R.string.ready_to_kill)
        binding.mainAction.setOnClickListener { prepareKilling() }
    }

    private fun prepareKilling() {
        gameFetcher.getGame(gameId) {
            game = it
            setOnPlayerClick { player, layout ->
                if (player == null) return@setOnPlayerClick
                if (player.isAlive) {
                    layout.role.setBackgroundColor(getColor(R.color.dead))
                    playerFetcher.killPlayer(gameId, player.name) {
                        updateGameField("${player.name} killed") {
                            prepareKilling()
                        }
                    }
                } else {
                    val color = if (player.isEvil) getColor(R.color.minion)
                    else getColor(R.color.townfolk)
                    layout.role.setBackgroundColor(color)
                    playerFetcher.resurrectPlayer(gameId, player.name) {
                        updateGameField("${player.name} resurrected") {
                            prepareKilling()
                        }
                    }
                }
            }
            binding.contentText.setText(R.string.ready_to_kill)
        }
    }
}
