package com.bar42.botcui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.bar42.botcui.databinding.ActivityMainBinding
import com.bar42.botcui.fetcher.GameFetcher
import com.bar42.botcui.model.enums.GameStatus

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val gameFetcher = GameFetcher(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        fillGameList(binding.gamesList)

        binding.add.setOnClickListener {
            gameFetcher.addGame {
                switchToGame(it.id!!)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun fillGameList(gamesList: ListView) {
        val adapter = ArrayAdapter<String>(this, R.layout.layout_game_item, mutableListOf())
        gamesList.adapter = adapter
        gameFetcher.getAll {games ->
            val gamesAvailable = games.filter { it.status != GameStatus.FINISHED }
                .map { "${it.id} : ${it.status}" }
            adapter.clear()
            adapter.addAll(gamesAvailable)
        }

        gamesList.setOnItemClickListener { parent, view, position, id ->
            val elementText = gamesList.adapter.getItem(position).toString()
            val gameId = elementText.split(":")[0].trim().toInt()
            switchToGame(gameId)
        }
    }

    private fun switchToGame(gameId: Int) {
        val intent = Intent(this, GameActivity::class.java)
        intent.putExtra("gameId", gameId)
        startActivity(intent)
    }
}