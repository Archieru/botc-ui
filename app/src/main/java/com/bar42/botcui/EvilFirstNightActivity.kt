package com.bar42.botcui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bar42.botcui.databinding.ActivityStartInfoBinding
import com.bar42.botcui.fetcher.GameFetcher
import com.bar42.botcui.fetcher.ImageFetcher
import com.bar42.botcui.model.enums.RoleType

class EvilFirstNightActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStartInfoBinding
    private val gameFetcher = GameFetcher(this)
    private val imageFetcher = ImageFetcher(this)
    private var gameId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        gameId = intent.getIntExtra("gameId", 0)

        binding.buttonShow.visibility = View.INVISIBLE
        binding.name.setText(R.string.minions_your_demon)
        binding.buttonNext.visibility = View.VISIBLE

        binding.buttonNext.setOnClickListener {
            binding.buttonNext.visibility = View.INVISIBLE
            binding.buttonShow.visibility = View.VISIBLE
            binding.name.setText(R.string.demon_your_minions)
            binding.buttonNext.setOnClickListener {
                val intent = Intent(this, FirstNightActivity::class.java)
                intent.putExtra("gameId", gameId)
                startActivity(intent)
            }
        }

        binding.back.setOnClickListener {
            val intent = Intent(this, DealedGameActivity::class.java)
            intent.putExtra("gameId", gameId)
            startActivity(intent)
        }

        binding.buttonShow.setOnClickListener {
            binding.name.visibility = View.INVISIBLE
            binding.buttonShow.visibility = View.INVISIBLE
            binding.role.visibility = View.VISIBLE
            binding.roleIcon.visibility = View.VISIBLE
            binding.description.visibility = View.VISIBLE
            binding.buttonNext.visibility = View.VISIBLE
            binding.bluffs.bluffs.visibility = View.VISIBLE
        }

        gameFetcher.getGame(gameId) {game ->
            val demonRole = game.players.first { it.role!!.type == RoleType.DEMON }.role!!
            binding.role.text = demonRole.name.name
            binding.roleIcon.setImageDrawable(imageFetcher.getDrawable(demonRole))
            binding.description.text = demonRole.description

            binding.bluffs.role1.setImageDrawable(imageFetcher.getDrawable(game.bluffs[0]))
            binding.bluffs.name1.text = game.bluffs[0].name
            binding.bluffs.role2.setImageDrawable(imageFetcher.getDrawable(game.bluffs[1]))
            binding.bluffs.name2.text = game.bluffs[1].name
            binding.bluffs.role3.setImageDrawable(imageFetcher.getDrawable(game.bluffs[2]))
            binding.bluffs.name3.text = game.bluffs[2].name
        }
    }
}