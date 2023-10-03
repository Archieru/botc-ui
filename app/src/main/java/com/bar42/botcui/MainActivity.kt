package com.bar42.botcui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.ui.AppBarConfiguration
import com.bar42.botcui.databinding.ActivityMainBinding
import com.bar42.botcui.fetcher.BaseFetcher
import com.bar42.botcui.model.Global
import com.bar42.botcui.model.enums.GameStatus
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        fillGameList(binding.gamesList)

        binding.add.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
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
        val adapter = ArrayAdapter<String>(this, R.layout.game_item_layout, mutableListOf())
        gamesList.adapter = adapter
        lifecycleScope.launch (Dispatchers.IO) {
            val gamesAvailable = BaseFetcher.gameInterface.getAll().body()!!
                .filter { it.status != GameStatus.FINISHED }
                .map { "${it.id} : ${it.status}" }
            withContext(Dispatchers.Main) {
                adapter.clear()
                adapter.addAll(gamesAvailable)
            }
        }
        gamesList.setOnItemClickListener { parent, view, position, id ->
            val elementText = gamesList.adapter.getItem(position).toString()
            val gameId = elementText.split(":")[0].trim().toInt()
            Global.gameId = gameId
//            findNavController().navigate(R.id.action_GameListFragment_to_GameFragment)
        }
    }
}