package com.bar42.botcui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bar42.botcui.databinding.ActivityStartInfoBinding
import com.bar42.botcui.fetcher.BaseFetcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StartInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityStartInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val gameId = intent.getIntExtra("gameId", 0)
        val playerNames = intent.getStringArrayListExtra("playerNames")!!.toMutableList()
        val playerName = playerNames.removeAt(0)

        binding.name.text = playerName
        binding.buttonShow.setOnClickListener {
            binding.buttonShow.visibility = View.INVISIBLE
            binding.role.visibility = View.VISIBLE
            binding.description.visibility = View.VISIBLE
            binding.buttonNext.visibility = View.VISIBLE
        }

        binding.buttonNext.setOnClickListener {
            if (playerNames.size == 0) {
                val intent = Intent(this, GameActivity::class.java)
                intent.putExtra("gameId", gameId)
                startActivity(intent)
            } else {
                val intent = Intent(this, StartInfoActivity::class.java)
                intent.putStringArrayListExtra("playerNames", ArrayList(playerNames))
                intent.putExtra("gameId", gameId)
                startActivity(intent)
            }
        }

        binding.back.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            intent.putExtra("gameId", gameId)
            startActivity(intent)
        }

        lifecycleScope.launch (Dispatchers.IO) {
            val role = BaseFetcher.playerInterface.getPlayerStartInfo(gameId, playerName).body()!!.role!!
            lifecycleScope.launch (Dispatchers.Main) {
                binding.role.text = role.name.name
                binding.description.text = role.description
            }
        }

    }
}