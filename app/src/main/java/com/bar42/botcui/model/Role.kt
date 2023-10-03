package com.bar42.botcui.model

import com.bar42.botcui.model.enums.RoleName
import com.bar42.botcui.model.enums.Scenario
import com.bar42.botcui.model.enums.RoleType

data class Role(
    val name: RoleName,
    val type: RoleType,
    val description: String,
    val scenario: Scenario = Scenario.TroubleBrewing,
)