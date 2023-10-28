package com.bar42.botcui

import android.os.Bundle
import com.bar42.botcui.fetcher.GameProgress
import com.bar42.botcui.model.enums.RoleName

open class BaseNightActivity : BaseGameActivity() {
    protected lateinit var sequence: MutableList<() -> Unit>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.progressGame.setText(R.string.button_start_day)
        binding.progressGame.setOnClickListener {
            gameFetcher.startDay(gameId) {
                GameProgress(game, this).proceedGame()
            }
        }
        binding.mainAction.setText(R.string.button_next_role)
        binding.mainAction.setOnClickListener { nextRole() }
    }

    protected fun nextRole() {
        if (sequence.isEmpty()) { return }
        updateGameField {
            sequence.removeAt(0).invoke()
        }
    }

    protected fun washerWoman() {
        val actor = game.players.firstOrNull { it.role == RoleName.WasherWoman && it.isAlive }
        if (actor == null) {
            nextRole()
            return
        }
        binding.contentText.text = "washerWoman"
    }

    protected fun librarian() {
        if (!game.players.any { it.role == RoleName.Librarian && it.isAlive }) {
            nextRole()
            return
        }
        binding.contentText.text = "librarian"
    }

    protected fun investigator() {
        if (!game.players.any { it.role == RoleName.Investigator && it.isAlive }) {
            nextRole()
            return
        }
        binding.contentText.text = "investigator"
    }

    protected fun chef() {
        if (!game.players.any { it.role == RoleName.Chef && it.isAlive }) {
            nextRole()
            return
        }

        binding.contentText.text = "chef"
    }

    protected fun dusk() {
        binding.contentText.text = "dusk"
    }

    protected fun poisoner() {
        val actor = game.players.firstOrNull { it.role == RoleName.Poisoner}
        if (actor == null || !actor.isAlive) {
            nextRole()
            return
        }
        binding.contentText.text = actor.role.name
        setOnPlayerClick { player, _ ->
            actor.target = player?.role ?: RoleName.Empty
            gameFetcher.saveGame(game) {
                updateGameField("${actor.role.name} ${actor.name} " +
                        "selected ${player!!.role.name} ${player.name}") {
                    poisoner()
                }
            }
        }
    }

    protected fun monk() {
        val actor = game.players.firstOrNull { it.role == RoleName.Monk}
        if (actor == null || !actor.isAlive) {
            nextRole()
            return
        }
        binding.contentText.text = actor.role.name
        setOnPlayerClick { player, _ ->
            actor.target = player?.role ?: RoleName.Empty
            gameFetcher.saveGame(game) {
                updateGameField("${actor.role.name} ${actor.name} " +
                        "selected ${player!!.role.name} ${player.name}") {
                    monk()
                }
            }
        }
    }

    protected fun scarletWoman() {
        val actor = game.players.firstOrNull { it.role == RoleName.ScarletWoman && it.isAlive }
        val target = game.players.firstOrNull { it.role == RoleName.Imp && !it.isAlive}
        val countAlive = game.players.count { it.isAlive }
        if (target == null || actor == null || countAlive < 5) {
            nextRole()
            return
        }
        actor.target = actor.role
        actor.role = target.role
        gameFetcher.saveGame(game) {
            updateGameField("New demon: ${actor.role.name} ${actor.name}")
        }
    }

    protected fun imp() {
        val actor = game.players.firstOrNull {
            it.role == RoleName.Imp && it.isAlive
        }
        if (actor == null) {
            nextRole()
            return
        }
        binding.contentText.text = actor.role.name
        setOnPlayerClick { player, _ ->
            actor.target = player?.role ?: RoleName.Empty
            val monk = game.players.firstOrNull { it.role == RoleName.Monk }
            val targetRole = player!!.role
            if (targetRole == RoleName.Soldier || (monk != null && monk.target == targetRole)) {
                return@setOnPlayerClick
            } else {
                player.isAlive = false
                gameFetcher.saveGame(game) {
                    updateGameField(
                        "${actor.role.name} ${actor.name} " +
                                "selected ${player.role.name} ${player.name}"
                    ) { imp() }
                }
            }
        }
    }

    protected fun ravenkeeper() {
        val actor = game.players.firstOrNull {
            it.role == RoleName.Ravenkeeper &&
            !it.isAlive && it.target == RoleName.Ravenkeeper
        }
        if (actor == null) {
            nextRole()
            return
        }
        actor.target = RoleName.Empty
        gameFetcher.saveGame(game) {
            updateGameField("Ravenkeeper chooses the player to know their role")
        }
    }

    protected fun empath() {
        val actor = game.players.firstOrNull { it.role == RoleName.Empath}
        if (actor == null || !actor.isAlive) {
            nextRole()
            return
        }
        updateGameField("Show to the Empath how many of his neighbours are evil")
    }

    protected fun fortuneTeller() {
        val actor = game.players.firstOrNull { it.role == RoleName.FortuneTeller}
        if (actor == null || !actor.isAlive) {
            nextRole()
            return
        }
        updateGameField("Fortune Teller chooses two people to know if there is a Demon among them")
    }

    protected fun undertaker() {
        val actor = game.players.firstOrNull { it.role == RoleName.Undertaker}
        if (actor == null || !actor.isAlive) {
            nextRole()
            return
        }
        updateGameField("Show the last one executed to an Undertaker")
    }

    protected fun butler() {
        val actor = game.players.firstOrNull { it.role == RoleName.Butler}
        if (actor == null || !actor.isAlive) {
            nextRole()
            return
        }
        binding.contentText.text = actor.role.name
        setOnPlayerClick { player, _ ->
            actor.target = player?.role ?: RoleName.Empty
            gameFetcher.saveGame(game) {
                updateGameField("${actor.role.name} ${actor.name} " +
                        "selected ${player!!.role.name} ${player.name}") {
                    butler()
                }
            }
        }
    }

    protected fun spy() {
        val actor = game.players.firstOrNull { it.role == RoleName.Undertaker}
        if (actor == null || !actor.isAlive) {
            nextRole()
            return
        }
        updateGameField("Show it to the Spy")
    }

}
