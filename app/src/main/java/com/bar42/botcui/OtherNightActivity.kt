package com.bar42.botcui

import android.os.Bundle
import com.bar42.botcui.fetcher.GameProgress
import com.bar42.botcui.model.enums.RoleName

class OtherNightActivity : BaseGameActivity() {
    private val sequence = mutableListOf(this::dusk,
        this::poisoner, this::monk, this::scarletWoman, this::imp, 
        this::ravenkeeper, this::empath, this::fortuneTeller, 
        this::undertaker, this::butler, this::spy, this::dawn
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

    private fun nextRole() {
        if (sequence.isEmpty()) { return }
        updateGameField {
            sequence.removeAt(0).invoke()
        }
    }

    private fun dusk() {
        binding.contentText.text = "dusk"
    }

    private fun poisoner() {
        if (!game.players.any { it.role!!.name == RoleName.Poisoner }) {
            nextRole()
            return
        }
        binding.contentText.text = "poisoner"
    }

    private fun monk() {
        if (!game.players.any { it.role!!.name == RoleName.WasherWoman }) {
            nextRole()
            return
        }
        binding.contentText.text = "monk"
    }

    private fun scarletWoman() {
        if (!game.players.any { it.role!!.name == RoleName.Librarian }) {
            nextRole()
            return
        }
        binding.contentText.text = "scarletWoman"
    }

    private fun imp() {
        if (!game.players.any { it.role!!.name == RoleName.Investigator }) {
            nextRole()
            return
        }
        binding.contentText.text = "imp"
    }

    private fun ravenkeeper() {
        if (!game.players.any { it.role!!.name == RoleName.Chef }) {
            nextRole()
            return
        }
        binding.contentText.text = "ravenkeeper"
    }

    private fun empath() {
        if (!game.players.any { it.role!!.name == RoleName.Empath }) {
            nextRole()
            return
        }
        binding.contentText.text = "empath"
    }

    private fun fortuneTeller() {
        if (!game.players.any { it.role!!.name == RoleName.FortuneTeller }) {
            nextRole()
            return
        }
        binding.contentText.text = "fortuneTeller"
    }

    private fun undertaker() {
        if (!game.players.any { it.role!!.name == RoleName.FortuneTeller }) {
            nextRole()
            return
        }
        binding.contentText.text = "undertaker"
    }

    private fun butler() {
        if (!game.players.any { it.role!!.name == RoleName.Butler }) {
            nextRole()
            return
        }
        binding.contentText.text = "butler"
    }

    private fun spy() {
        if (!game.players.any { it.role!!.name == RoleName.Spy }) {
            nextRole()
            return
        }
        binding.contentText.text = "spy"
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