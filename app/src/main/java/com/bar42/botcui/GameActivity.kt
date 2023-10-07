package com.bar42.botcui

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bar42.botcui.databinding.ActivityGameBinding
import com.bar42.botcui.fetcher.BaseFetcher
import com.bar42.botcui.fetcher.GameInterface
import com.bar42.botcui.fetcher.PlayerInterface
import com.bar42.botcui.model.Game
import com.bar42.botcui.model.Player
import com.bar42.botcui.model.enums.GameStatus
import com.bar42.botcui.model.enums.RoleName
import com.bar42.botcui.model.enums.Scenario
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class GameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameBinding
    private lateinit var contentText: TextView
    private lateinit var game: Game
    private var gameId: Int = 0
    private var seated = mapOf<Int, Player>()
    private val gameInterface: GameInterface = BaseFetcher.gameInterface
    private val playerInterface: PlayerInterface = BaseFetcher.playerInterface

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gameId = intent.getIntExtra("gameId", 0)
        updateGameField()

        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        contentText = binding.contentText
        contentText.setOnClickListener {
            proceedGame()
            updateGameField()
        }

        binding.addPlayer.setOnClickListener {
            createPlayerDialog()
            updateGameField()
        }

        binding.back.setOnClickListener {
           startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun proceedGame() {
        if (!this::game.isInitialized) { return }
        when (game.status) {
            GameStatus.CREATED -> dealGame()
            GameStatus.LOCKED -> return
            GameStatus.DEALED -> startGame()
            GameStatus.STARTED -> finishGame()
            GameStatus.FINISHED -> return
        }
    }

    private fun finishGame() {
        TODO("Not yet implemented")
    }

    private fun startGame() {
        val playerNames = game.players.map { it.name }
        val intent = Intent(this, StartInfoActivity::class.java)
        intent.putStringArrayListExtra("playerNames", ArrayList(playerNames))
        intent.putExtra("gameId", gameId)
        startActivity(intent)
    }

    private fun dealGame() {
        lifecycleScope.launch (Dispatchers.IO) {
            gameInterface.dealGame(gameId, Scenario.TroubleBrewing)
        }
    }

    private fun createPlayerDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.player_name)
        val input = EditText(this)
        input.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(input)

        builder.setPositiveButton(R.string.dialog_ok) { _, _ ->
            lifecycleScope.launch (Dispatchers.IO) {
                playerInterface.addPlayerToGame(gameId, input.text.toString())
            }
        }

        builder.setNegativeButton(R.string.dialog_cancel) { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }

    private fun updateGameField() {
        lifecycleScope.launch (Dispatchers.IO) {
            game = gameInterface.getGame(gameId).body()!!
            lifecycleScope.launch (Dispatchers.Main) {
                seated = seatPlayers(game.players)
                var statusText = "Game ${game.id} : ${game.status}"
                for (player in game.players) { statusText += "\n${player.name}" }
                binding.contentText.text = statusText
            }
        }
    }

    private fun seatPlayers(players: List<Player>): Map<Int, Player> {
        val ret = mutableMapOf<Int, Player>()
        val seatPriority = listOf(3, 8, 9, 12, 16, 1, 5, 10, 11, 6, 7, 2, 4, 13, 15, 14)
        val freeSeats = mutableListOf(14, 13, 12, 10, 8, 6, 1, 2, 3, 4, 5, 7, 9, 11, 16, 15)

        hideButtons()

        val occupiedSeats = mutableListOf(14, 13, 12, 10, 8, 6, 1, 2, 3, 4, 5, 7, 9, 11, 16, 15)
        repeat(players.size) { freeSeats.remove(seatPriority[it]) }
        occupiedSeats.removeAll(freeSeats)

        players.forEachIndexed { index, player ->
            val seat = occupiedSeats[index]
            ret[seat] = player
            when(seat) {
                1  -> { populateButtons(player, binding.player1, binding.target1) }
                2  -> { populateButtons(player, binding.player2, binding.target2) }
                3  -> { populateButtons(player, binding.player3, binding.target3) }
                4  -> { populateButtons(player, binding.player4, binding.target4) }
                5  -> { populateButtons(player, binding.player5, binding.target5) }
                6  -> { populateButtons(player, binding.player6, binding.target6) }
                7  -> { populateButtons(player, binding.player7, binding.target7) }
                8  -> { populateButtons(player, binding.player8, binding.target8) }
                9  -> { populateButtons(player, binding.player9, binding.target9) }
                10  -> { populateButtons(player, binding.player10, binding.target10) }
                11  -> { populateButtons(player, binding.player11, binding.target11) }
                12  -> { populateButtons(player, binding.player12, binding.target12) }
                13  -> { populateButtons(player, binding.player13, binding.target13) }
                14  -> { populateButtons(player, binding.player14, binding.target14) }
                15  -> { populateButtons(player, binding.player15, binding.target15) }
                16  -> { populateButtons(player, binding.player16, binding.target16) }
            }
        }
        return ret
    }

    private fun hideButtons() {
        binding.player1.visibility = View.INVISIBLE
        binding.target1.visibility = View.INVISIBLE
        binding.player2.visibility = View.INVISIBLE
        binding.target2.visibility = View.INVISIBLE
        binding.player3.visibility = View.INVISIBLE
        binding.target3.visibility = View.INVISIBLE
        binding.player4.visibility = View.INVISIBLE
        binding.target4.visibility = View.INVISIBLE
        binding.player5.visibility = View.INVISIBLE
        binding.target5.visibility = View.INVISIBLE
        binding.player6.visibility = View.INVISIBLE
        binding.target6.visibility = View.INVISIBLE
        binding.player7.visibility = View.INVISIBLE
        binding.target7.visibility = View.INVISIBLE
        binding.player8.visibility = View.INVISIBLE
        binding.target8.visibility = View.INVISIBLE
        binding.player9.visibility = View.INVISIBLE
        binding.target9.visibility = View.INVISIBLE
        binding.player10.visibility = View.INVISIBLE
        binding.target10.visibility = View.INVISIBLE
        binding.player11.visibility = View.INVISIBLE
        binding.target11.visibility = View.INVISIBLE
        binding.player12.visibility = View.INVISIBLE
        binding.target12.visibility = View.INVISIBLE
        binding.player13.visibility = View.INVISIBLE
        binding.target13.visibility = View.INVISIBLE
        binding.player14.visibility = View.INVISIBLE
        binding.target14.visibility = View.INVISIBLE
        binding.player15.visibility = View.INVISIBLE
        binding.target15.visibility = View.INVISIBLE
        binding.player16.visibility = View.INVISIBLE
        binding.target16.visibility = View.INVISIBLE
    }

    private fun populateButtons(player: Player, playerButton: Button, targetButton: Button) {
        val roleName = if (player.role == null || player.role!!.name == RoleName.Empty) "" else player.role!!.name.name
        val buttonText = "${player.name}\n$roleName"
        val targetText = if (player.target == RoleName.Empty) "" else player.target.name

        playerButton.text = buttonText
        playerButton.visibility = View.VISIBLE
        val color = if (player.isEvil) getColor(R.color.minion) else getColor(R.color.townfolk)
        playerButton.setBackgroundColor(color)
        targetButton.text = targetText
        targetButton.visibility = View.VISIBLE
    }
}