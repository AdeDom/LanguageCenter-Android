package com.lc.library.domain.repository

import com.lc.server.models.request.SignInRequest
import com.lc.server.models.response.SignInResponse

interface LanguageCenterRepository {

    suspend fun signIn(request: SignInRequest): SignInResponse?

}
