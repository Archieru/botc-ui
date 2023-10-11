package com.bar42.botcui

import android.content.Intent
import android.os.Bundle

class DealedGameActivity : GameActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.progressGame.setText(R.string.button_start_first_night)
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