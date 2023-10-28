package com.bar42.botcui

import android.os.Bundle
import com.bar42.botcui.fetcher.GameProgress

class OtherNightActivity : BaseNightActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sequence = mutableListOf(this::dusk,
            this::poisoner, this::monk, this::scarletWoman, this::imp,
            this::ravenkeeper, this::empath, this::fortuneTeller,
            this::undertaker, this::butler, this::spy, this::dawn
        )
        binding.contentText.text = "OtherNightActivity"
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
                GameProgress(game, this).proceedGame()
            }
            binding.progressGame.setOnClickListener {
                GameProgress(game, this).proceedGame()
            }
        }
    }
}