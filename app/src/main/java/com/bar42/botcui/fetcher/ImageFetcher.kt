package com.bar42.botcui.fetcher

import android.content.res.Resources
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bar42.botcui.model.Role
import com.bar42.botcui.model.enums.RoleName

class ImageFetcher(context: AppCompatActivity): BaseFetcher(context) {
    fun getDrawable(role: Role?): Drawable {
        val roleName = role?.name ?: RoleName.Empty
        return getDrawable(roleName)
    }

    fun getDrawable(roleName: RoleName): Drawable {
        val resources: Resources = context.resources
        val resourceId = resources.getIdentifier(
            "icon_${roleName.name.lowercase()}",
            "drawable",
            context.packageName
        )
        return ContextCompat.getDrawable(context.applicationContext, resourceId)!!
    }
}