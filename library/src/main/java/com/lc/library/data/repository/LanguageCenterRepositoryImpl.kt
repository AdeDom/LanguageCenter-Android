package com.lc.library.data.repository

import com.lc.library.data.db.entities.UserInfoEntity
import com.lc.library.data.network.source.LanguageCenterDataSource
import com.lc.library.domain.repository.LanguageCenterRepository
import com.lc.library.sharedpreference.pref.PreferenceAuth
import com.lc.server.models.request.SignInRequest
import com.lc.server.models.response.SignInResponse
import com.lc.server.models.response.UserInfoResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class LanguageCenterRepositoryImpl(
    private val dataSource: LanguageCenterDataSource,
    private val pref: PreferenceAuth,
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
        val response = safeApiCall { dataSource.callSignIn(request) }

        if (response is Resource.Success) {
            saveTokenAuth(response.data)
            callFetchUserInfo()
        }

        return response
    }

    private fun saveTokenAuth(response: SignInResponse) {
        if (response.success) {
            pref.accessToken = response.token?.accessToken.orEmpty()
            pref.refreshToken = response.token?.refreshToken.orEmpty()
        }
    }

    override suspend fun callFetchUserInfo(): Resource<UserInfoResponse> {
        val response = safeApiCall { dataSource.callFetchUserInfo() }

        if (response is Resource.Success) saveUserInfoDb(response.data)

        return response
    }

    private suspend fun saveUserInfoDb(data: UserInfoResponse) {
        if (data.success) {
            val userInfo = data.userInfo
            val entity = UserInfoEntity(
                userId = userInfo?.userId.orEmpty(),
                email = userInfo?.email,
                givenName = userInfo?.givenName,
                familyName = userInfo?.familyName,
                name = userInfo?.name,
                picture = userInfo?.picture,
                gender = userInfo?.gender,
                birthDate = userInfo?.birthDate,
                verifiedEmail = userInfo?.verifiedEmail ?: false,
                aboutMe = userInfo?.aboutMe,
                created = userInfo?.created,
                updated = userInfo?.updated,
                localNatives = userInfo?.localNatives ?: emptyList(),
                localLearnings = userInfo?.localLearnings ?: emptyList(),
            )
            dataSource.saveUserInfo(entity)
        }
    }

    override suspend fun signOut() {
        pref.accessToken = ""
        pref.refreshToken = ""
        dataSource.deleteUserInfo()
    }

}
