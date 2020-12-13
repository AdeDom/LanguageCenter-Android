package com.lc.library.data.repository

import com.lc.library.data.network.source.LanguageCenterDataSource
import com.lc.library.domain.repository.LanguageCenterRepository
import com.lc.server.models.request.SignInRequest
import com.lc.server.models.response.SignInResponse
import com.lc.server.models.response.UserInfoResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class LanguageCenterRepositoryImpl(
    private val dataSource: LanguageCenterDataSource
) : LanguageCenterRepository {

    private suspend fun <T : Any> safeApiCall(apiCall: suspend () -> T): Resource<T> =
        withContext(Dispatchers.IO) {
            try {
                Resource.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                if (throwable is HttpException && (throwable.code() == 401 || throwable.code() == 403)) {
                    Resource.Error(throwable, true)
                } else {
                    Resource.Error(throwable, false)
                }
            }
        }

    override suspend fun callSignIn(request: SignInRequest): Resource<SignInResponse> {
        return safeApiCall { dataSource.callSignIn(request) }
    }

    override suspend fun callFetchUserInfo(): Resource<UserInfoResponse> {
        return safeApiCall { dataSource.callFetchUserInfo() }
    }

}
