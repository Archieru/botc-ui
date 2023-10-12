package com.bar42.botcui

import android.os.Bundle
import com.bar42.botcui.model.enums.RoleName

class FirstNightActivity : BaseGameActivity() {
    private val sequence = mutableListOf(
        this::poisoner, this::washerWoman,
        this::librarian, this::investigator, this::chef, this::empath,
        this::fortuneTeller, this::butler, this::spy, this::dawn
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.progressGame.setText(R.string.button_start_day)
        binding.mainAction.setText(R.string.button_next_role)
        binding.mainAction.setOnClickListener { nextRole() }
    }

    private fun nextRole() {
        if (sequence.isEmpty()) { return }
        sequence.removeAt(0).invoke()
        updateGameField()
    }

    private fun poisoner() {
        if (!game.players.any { it.role!!.name == RoleName.Poisoner }) {
            nextRole()
            return
        }
        binding.contentText.text = "poisoner"
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
        if (!game.players.any { it.role!!.name == RoleName.Chef }) { nextRole() }
        binding.contentText.text = "chef"
    }

    private fun empath() {
        if (!game.players.any { it.role!!.name == RoleName.Empath }) { nextRole() }
        binding.contentText.text = "empath"
    }

    private fun fortuneTeller() {
        if (!game.players.any { it.role!!.name == RoleName.FortuneTeller }) { nextRole() }
        binding.contentText.text = "fortuneTeller"
    }

    private fun butler() {
        if (!game.players.any { it.role!!.name == RoleName.Butler }) { nextRole() }
        binding.contentText.text = "butler"
    }

    private fun spy() {
        if (!game.players.any { it.role!!.name == RoleName.Spy }) { nextRole() }
        binding.contentText.text = "spy"
    }

    private fun dawn() {
        binding.contentText.text = "dawn"
    }
}