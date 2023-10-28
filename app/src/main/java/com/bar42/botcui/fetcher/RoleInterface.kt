package com.bar42.botcui.fetcher

import com.bar42.botcui.model.Role
import com.bar42.botcui.model.enums.RoleName
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RoleInterface {
    @GET("roles/{name}")
    suspend fun getRole(@Path("name") name: RoleName): Response<Role>
}