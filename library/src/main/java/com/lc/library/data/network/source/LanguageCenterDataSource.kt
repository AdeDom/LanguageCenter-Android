package com.lc.library.data.network.source

import com.lc.server.models.request.SignInRequest
import com.lc.server.models.response.SignInResponse
import com.lc.server.models.response.UserInfoResponse

interface LanguageCenterDataSource {

    suspend fun callSignIn(request: SignInRequest): SignInResponse

    suspend fun callFetchUserInfo(): UserInfoResponse

}
