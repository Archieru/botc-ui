package com.bar42.botcui.model

import com.bar42.botcui.model.enums.GameStatus
import com.bar42.botcui.model.enums.RoleName

data class Game(
    val id: Int? = null,
    var players: MutableList<Player> = mutableListOf(),
    val bag: MutableList<RoleName> = mutableListOf(),
    val bluffs: MutableList<RoleName> = mutableListOf(),
    var status: GameStatus = GameStatus.CREATED
)
