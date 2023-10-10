package com.bar42.botcui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bar42.botcui.databinding.ActivityStartInfoBinding
import com.bar42.botcui.databinding.LayoutBluffBinding
import com.bar42.botcui.fetcher.GameFetcher
import com.bar42.botcui.fetcher.ImageFetcher
import com.bar42.botcui.fetcher.PlayerFetcher
import com.bar42.botcui.model.enums.RoleName
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
        binding.buttonShow.setOnClickListener {
            binding.buttonShow.visibility = View.INVISIBLE
            binding.role.visibility = View.VISIBLE
            binding.roleIcon.visibility = View.VISIBLE
            binding.description.visibility = View.VISIBLE
            binding.buttonNext.visibility = View.VISIBLE
            binding.bluffs.visibility = View.VISIBLE
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

        playerFetcher.getPlayerStartInfo(gameId, playerName) {
            val role = it.role!!

            binding.role.text = role.name.name
            binding.roleIcon.setImageDrawable(imageFetcher.getDrawable(role))
            binding.description.text = role.description

            if (role.type == RoleType.DEMON) {
                fillBluffs()
            }
        }
    }

    private fun fillBluffs() {
        fun fillBluff(layout: LayoutBluffBinding, bluff: RoleName) {
            layout.role.setImageDrawable(imageFetcher.getDrawable(bluff))
            layout.name.text = bluff.name
            layout.role.visibility = View.VISIBLE
            layout.name.visibility = View.VISIBLE
        }

        val gameFetcher = GameFetcher(this)
        gameFetcher.getGame(gameId) {
            fillBluff(binding.bluff1, it.bluffs[0])
            fillBluff(binding.bluff2, it.bluffs[1])
            fillBluff(binding.bluff3, it.bluffs[2])
        }
    }
}