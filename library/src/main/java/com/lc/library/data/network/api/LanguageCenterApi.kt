package com.lc.library.data.network.api

import com.lc.server.models.request.SignInRequest
import com.lc.server.models.response.SignInResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface LanguageCenterApi {

    @POST("api/auth/sign-in")
    suspend fun signIn(@Body request: SignInRequest): SignInResponse

}
