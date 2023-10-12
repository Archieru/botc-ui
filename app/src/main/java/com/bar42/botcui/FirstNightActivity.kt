package com.bar42.botcui

import android.os.Bundle
import com.bar42.botcui.fetcher.GameProgress
import com.bar42.botcui.model.enums.GameStatus
import com.bar42.botcui.model.enums.RoleName

class FirstNightActivity : BaseGameActivity() {
    private val sequence = mutableListOf(
        this::poisoner, this::washerWoman,
        this::librarian, this::investigator, this::chef, this::empath,
        this::fortuneTeller, this::butler, this::spy, this::dawn
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

    private fun nextRole() {
        if (sequence.isEmpty()) { return }
        updateGameField {
            sequence.removeAt(0).invoke()
        }
    }

    private fun poisoner() {
        val actor = game.players.firstOrNull { it.role!!.name == RoleName.Poisoner}
        if (actor == null || !actor.isAlive) {
            nextRole()
            return
        }
        binding.contentText.text = actor.role!!.name.name
        setOnPlayerClick { player, _ ->
            actor.target = player?.role?.name ?: RoleName.Empty
            gameFetcher.saveGame(game) {
                updateGameField("${actor.role!!.name.name} ${actor.name} " +
                        "selected ${player!!.role!!.name} ${player!!.name}")
            }
        }
    }

    private fun washerWoman() {
        if (!game.players.any { it.role!!.name == RoleName.WasherWoman }) {
            nextRole()
            return
        }
        binding.contentText.text = "washerWoman"
    }

    private fun librarian() {
        if (!game.players.any { it.role!!.name == RoleName.Librarian }) {
            nextRole()
            return
        }
        binding.contentText.text = "librarian"
    }

    private fun investigator() {
        if (!game.players.any { it.role!!.name == RoleName.Investigator }) {
            nextRole()
            return
        }
        binding.contentText.text = "investigator"
    }

    private fun chef() {
        if (!game.players.any { it.role!!.name == RoleName.Chef }) {
            nextRole()
            return
        }

        binding.contentText.text = "chef"
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
                GameProgress(gameId, GameStatus.FIRST_NIGHT, this).proceedGame()
            }
            binding.progressGame.setOnClickListener {
                GameProgress(gameId, GameStatus.FIRST_NIGHT, this).proceedGame()
            }
        }
    }
}
