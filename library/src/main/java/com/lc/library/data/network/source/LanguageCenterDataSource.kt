package com.lc.library.data.network.source

import androidx.lifecycle.LiveData
import com.lc.library.data.db.entities.UserInfoEntity
import com.lc.server.models.request.SignInRequest
import com.lc.server.models.response.SignInResponse
import com.lc.server.models.response.UserInfoResponse

interface LanguageCenterDataSource {

    suspend fun saveUserInfo(userInfo: UserInfoEntity)

    suspend fun getDbUserInfo(): UserInfoEntity?

    fun getDbUserInfoLiveData(): LiveData<UserInfoEntity>

    suspend fun deleteUserInfo()

    suspend fun callSignIn(request: SignInRequest): SignInResponse

    suspend fun callFetchUserInfo(): UserInfoResponse

}
