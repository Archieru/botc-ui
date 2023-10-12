package com.bar42.botcui

import android.content.Intent
import android.os.Bundle
import com.bar42.botcui.fetcher.GameProgress
import com.bar42.botcui.model.enums.GameStatus

class DealedGameActivity : BaseGameActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.progressGame.setText(R.string.button_start_first_night)
        binding.progressGame.setOnClickListener {
            gameFetcher.startGame(gameId) {
                GameProgress(gameId, GameStatus.DEALED, this).proceedGame()
            }
        }
        binding.mainAction.setText(R.string.button_show_roles)
        binding.mainAction.setOnClickListener { showRoles() }
    }

    private fun showRoles() {
        val playerNames = game.players.map { it.name }
        val intent = Intent(this, StartInfoActivity::class.java)
        intent.putStringArrayListExtra("playerNames", ArrayList(playerNames))
        intent.putExtra("gameId", gameId)
        this.startActivity(intent)
    }
}