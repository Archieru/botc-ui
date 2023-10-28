package com.bar42.botcui

import android.os.Bundle
import com.bar42.botcui.fetcher.GameProgress
import com.bar42.botcui.model.enums.GameStatus

class FirstNightActivity : BaseNightActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sequence = mutableListOf(
            this::poisoner, this::washerWoman,
            this::librarian, this::investigator, this::chef, this::empath,
            this::fortuneTeller, this::butler, this::spy, this::dawn
        )

        binding.contentText.text = "FirstNightActivity"
        binding.progressGame.setText(R.string.button_start_day)
        binding.progressGame.setOnClickListener {
            gameFetcher.startDay(gameId) {
                GameProgress(game, this).proceedGame()
            }
        }
        binding.mainAction.setText(R.string.button_next_role)
        binding.mainAction.setOnClickListener { nextRole() }
    }

    private fun dawn() {
        binding.contentText.text = "dawn"
        gameFetcher.startDay(gameId) {
            binding.mainAction.setOnClickListener {
                GameProgress(gameId, GameStatus.FIRST_NIGHT, this).proceedGame()
            }
            binding.progressGame.setOnClickListener {
                GameProgress(gameId, GameStatus.FIRST_NIGHT, this).proceedGame()
            }
        }
    }

}
