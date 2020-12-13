package com.lc.library.data.network.source

import com.lc.server.models.request.SignInRequest
import com.lc.server.models.response.SignInResponse

interface LanguageCenterDataSource {

    suspend fun signIn(request: SignInRequest): SignInResponse

}
