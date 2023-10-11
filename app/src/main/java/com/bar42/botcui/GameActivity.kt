package com.bar42.botcui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bar42.botcui.databinding.ActivityGameBinding
import com.bar42.botcui.databinding.LayoutPlayerBinding
import com.bar42.botcui.fetcher.GameFetcher
import com.bar42.botcui.fetcher.GameProgress
import com.bar42.botcui.fetcher.ImageFetcher
import com.bar42.botcui.fetcher.PlayerFetcher
import com.bar42.botcui.model.Game
import com.bar42.botcui.model.Player

open class GameActivity : AppCompatActivity() {
    protected val gameFetcher = GameFetcher(this)
    protected val playerFetcher = PlayerFetcher(this)
    protected val imageFetcher = ImageFetcher(this)

    protected var gameId: Int = 0
    protected lateinit var game: Game
    protected lateinit var binding: ActivityGameBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gameId = intent.getIntExtra("gameId", 0)
        updateGameField()

        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.progressGame.setOnClickListener {
            GameProgress(game, this).proceedGame()
        }

        binding.back.setOnClickListener {
           startActivity(Intent(this, MainActivity::class.java))
        }
    }

    protected fun updateGameField() {
        gameFetcher.getGame(gameId) {
            hideButtons()
            seatPlayers(it.players)

            var gameStatusText = "Game ${it.id} : ${it.status}"
            for (player in it.players) { gameStatusText += " [${player.name}]" }
            binding.contentText.text = gameStatusText

            game = it
            Any()
        }
    }

    protected fun seatPlayers(players: List<Player>): Map<Int, Player> {
        val ret = mutableMapOf<Int, Player>()
        val seatPriority = listOf(3, 8, 9, 13, 15, 2, 4, 10, 11, 6, 7, 1, 5, 12, 16, 14)
        val freeSeats = mutableListOf(14, 13, 12, 10, 8, 6, 1, 2, 3, 4, 5, 7, 9, 11, 16, 15)

        val occupiedSeats = mutableListOf(14, 13, 12, 10, 8, 6, 1, 2, 3, 4, 5, 7, 9, 11, 16, 15)
        repeat(players.size) { freeSeats.remove(seatPriority[it]) }
        occupiedSeats.removeAll(freeSeats)

        players.forEachIndexed { index, player ->
            val seat = occupiedSeats[index]
            ret[seat] = player
            when(seat) {
                1  -> { populateButtons(player, binding.player1) }
                2  -> { populateButtons(player, binding.player2) }
                3  -> { populateButtons(player, binding.player3) }
                4  -> { populateButtons(player, binding.player4) }
                5  -> { populateButtons(player, binding.player5) }
                6  -> { populateButtons(player, binding.player6) }
                7  -> { populateButtons(player, binding.player7) }
                8  -> { populateButtons(player, binding.player8) }
                9  -> { populateButtons(player, binding.player9) }
                10  -> { populateButtons(player, binding.player10) }
                11  -> { populateButtons(player, binding.player11) }
                12  -> { populateButtons(player, binding.player12) }
                13  -> { populateButtons(player, binding.player13) }
                14  -> { populateButtons(player, binding.player14) }
                15  -> { populateButtons(player, binding.player15) }
                16  -> { populateButtons(player, binding.player16) }
            }
        }
        return ret
    }

    protected fun hideButtons() {
        binding.player1.container.visibility = View.INVISIBLE
        binding.player2.container.visibility = View.INVISIBLE
        binding.player3.container.visibility = View.INVISIBLE
        binding.player4.container.visibility = View.INVISIBLE
        binding.player5.container.visibility = View.INVISIBLE
        binding.player6.container.visibility = View.INVISIBLE
        binding.player7.container.visibility = View.INVISIBLE
        binding.player8.container.visibility = View.INVISIBLE
        binding.player9.container.visibility = View.INVISIBLE
        binding.player10.container.visibility = View.INVISIBLE
        binding.player11.container.visibility = View.INVISIBLE
        binding.player12.container.visibility = View.INVISIBLE
        binding.player13.container.visibility = View.INVISIBLE
        binding.player14.container.visibility = View.INVISIBLE
        binding.player15.container.visibility = View.INVISIBLE
        binding.player16.container.visibility = View.INVISIBLE
    }

    protected fun populateButtons(player: Player, playerLayout: LayoutPlayerBinding) {
        playerLayout.container.visibility = View.VISIBLE
        playerLayout.name.text = player.name
        playerLayout.role.setImageDrawable(imageFetcher.getDrawable(player.role))
        val color = if (player.isEvil) getColor(R.color.minion) else getColor(R.color.townfolk)
        playerLayout.role.setBackgroundColor(color)
        playerLayout.target.setImageDrawable(imageFetcher.getDrawable(player.target))
    }
}