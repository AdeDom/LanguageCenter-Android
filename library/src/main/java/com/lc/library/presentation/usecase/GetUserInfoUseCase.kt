package com.lc.library.presentation.usecase

import androidx.lifecycle.LiveData
import com.lc.library.data.db.entities.UserInfoEntity
import com.lc.library.data.repository.Resource
import com.lc.server.models.response.UserInfoResponse

interface GetUserInfoUseCase {

    suspend fun callFetchUserInfo(): Resource<UserInfoResponse>?

    fun getDbUserInfoLiveData(): LiveData<UserInfoEntity>

    suspend fun getDbUserInfo(): UserInfoEntity?

}
