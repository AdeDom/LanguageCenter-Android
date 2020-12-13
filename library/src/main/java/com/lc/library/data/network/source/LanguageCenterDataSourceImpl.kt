package com.lc.library.data.network.source

import com.lc.server.models.request.SignInRequest
import com.lc.server.models.response.SignInResponse

class LanguageCenterDataSourceImpl(
    private val provider: DataSourceProvider
) : LanguageCenterDataSource {

    override suspend fun signIn(request: SignInRequest): SignInResponse {
        return provider.getDataSource().signIn(request)
    }

}
