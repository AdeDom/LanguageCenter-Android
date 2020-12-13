package com.lc.library.data.network.source

import com.lc.server.models.request.SignInRequest
import com.lc.server.models.response.SignInResponse
import com.lc.server.models.response.UserInfoResponse

class LanguageCenterDataSourceImpl(
    private val provider: DataSourceProvider
) : LanguageCenterDataSource {

    override suspend fun callSignIn(request: SignInRequest): SignInResponse {
        return provider.getDataSource().callSignIn(request)
    }

    override suspend fun callFetchUserInfo(): UserInfoResponse {
        return provider.getLanguageCenterDataSource().callFetchUserInfo()
    }

}
