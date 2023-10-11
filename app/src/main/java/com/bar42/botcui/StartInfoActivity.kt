package com.bar42.botcui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bar42.botcui.databinding.ActivityStartInfoBinding
import com.bar42.botcui.fetcher.GameFetcher
import com.bar42.botcui.fetcher.ImageFetcher
import com.bar42.botcui.fetcher.PlayerFetcher
import com.bar42.botcui.model.enums.RoleType

class StartInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStartInfoBinding
    private val playerFetcher = PlayerFetcher(this)
    private val imageFetcher = ImageFetcher(this)
    private var gameId: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        gameId = intent.getIntExtra("gameId", 0)
        val playerNames = intent.getStringArrayListExtra("playerNames")!!.toMutableList()
        val playerName = playerNames.removeAt(0)

        binding.name.text = playerName

        binding.buttonNext.setOnClickListener {
            if (playerNames.size == 0) {
                val intent = Intent(this, DealedGameActivity::class.java)
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
            val intent = Intent(this, DealedGameActivity::class.java)
            intent.putExtra("gameId", gameId)
            startActivity(intent)
        }

        playerFetcher.getPlayerStartInfo(gameId, playerName) {
            val role = it.role!!

            binding.role.text = role.name.name
            binding.roleIcon.setImageDrawable(imageFetcher.getDrawable(role))
            binding.description.text = role.description

            if (role.type == RoleType.DEMON) {
                fillBluffs()
            }
            binding.buttonShow.setOnClickListener {
                binding.buttonShow.visibility = View.INVISIBLE
                binding.role.visibility = View.VISIBLE
                binding.roleIcon.visibility = View.VISIBLE
                binding.description.visibility = View.VISIBLE
                binding.buttonNext.visibility = View.VISIBLE
                if (role.type == RoleType.DEMON) {
                    binding.bluffs.bluffs.visibility = View.VISIBLE
                }
            }

        }
    }

    private fun fillBluffs() {
        val gameFetcher = GameFetcher(this)
        gameFetcher.getGame(gameId) {
            binding.bluffs.role1.setImageDrawable(imageFetcher.getDrawable(it.bluffs[0]))
            binding.bluffs.name1.text = it.bluffs[0].name
            binding.bluffs.role2.setImageDrawable(imageFetcher.getDrawable(it.bluffs[1]))
            binding.bluffs.name2.text = it.bluffs[1].name
            binding.bluffs.role3.setImageDrawable(imageFetcher.getDrawable(it.bluffs[2]))
            binding.bluffs.name3.text = it.bluffs[2].name
        }
    }
}