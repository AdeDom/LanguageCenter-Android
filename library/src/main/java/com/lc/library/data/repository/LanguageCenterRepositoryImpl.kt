package com.lc.library.data.repository

import com.lc.library.data.network.source.LanguageCenterDataSource
import com.lc.library.domain.repository.LanguageCenterRepository
import com.lc.server.models.request.SignInRequest
import com.lc.server.models.response.SignInResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LanguageCenterRepositoryImpl(
    private val dataSource: LanguageCenterDataSource
) : LanguageCenterRepository {

    private suspend fun <T : Any> safeApiCall(apiCall: suspend () -> T): T? =
        withContext(Dispatchers.IO) {
            try {
                apiCall()
            } catch (throwable: Throwable) {
                null
            }
        }

    override suspend fun signIn(request: SignInRequest): SignInResponse? {
        return safeApiCall { dataSource.signIn(request) }
    }

}
