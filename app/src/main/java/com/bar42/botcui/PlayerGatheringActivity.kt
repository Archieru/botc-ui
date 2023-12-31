package com.bar42.botcui

import android.app.AlertDialog
import android.os.Bundle
import android.text.InputType
import android.widget.EditText

class PlayerGatheringActivity : BaseGameActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.progressGame.setText(R.string.deal_game)

        binding.mainAction.setOnClickListener {
            createPlayerDialog()
        }
    }

    private fun createPlayerDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.player_name)
        val input = EditText(this)
        input.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(input)

        builder.setPositiveButton(R.string.dialog_ok) { _, _ ->
            val playerName = input.text.toString()
            playerFetcher.addPlayerToGame(gameId, playerName) {
                updateGameField(getString(R.string.player_added, playerName))
            }
        }

        builder.setNegativeButton(R.string.dialog_cancel) { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }
}