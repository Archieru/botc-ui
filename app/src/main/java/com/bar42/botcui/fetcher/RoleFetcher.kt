package com.bar42.botcui.fetcher

import androidx.appcompat.app.AppCompatActivity
import com.bar42.botcui.model.Role
import com.bar42.botcui.model.enums.RoleName

class RoleFetcher(context: AppCompatActivity): BaseFetcher(context) {
    private val roleInterface = retrofit.create(RoleInterface::class.java)
    fun getRole(name: RoleName, callback: (Role) -> Any) {
        executeCallback(callback) {
            roleInterface.getRole(name)
        }
    }
}