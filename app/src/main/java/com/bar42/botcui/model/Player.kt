package com.bar42.botcui.model

import com.bar42.botcui.model.enums.RoleName

data class Player(
    val name: String,
    val gameId: Int? = null,
    val id: Int? = null,
    var role: RoleName = RoleName.Empty,
    var isEvil: Boolean = false,
    var isAlive: Boolean = true,
    var isGhostVote: Boolean = true,
    var target: RoleName = RoleName.Empty
)