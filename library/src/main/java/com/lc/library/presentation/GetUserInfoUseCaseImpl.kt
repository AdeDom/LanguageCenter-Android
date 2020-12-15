package com.lc.library.presentation

import androidx.lifecycle.LiveData
import com.lc.library.data.db.entities.UserInfoEntity
import com.lc.library.data.network.source.LanguageCenterDataSource
import com.lc.library.data.repository.Resource
import com.lc.library.domain.repository.LanguageCenterRepository
import com.lc.server.models.response.UserInfoResponse

class GetUserInfoUseCaseImpl(
    private val dataSource: LanguageCenterDataSource,
    private val repository: LanguageCenterRepository,
) : GetUserInfoUseCase {

    override suspend fun callFetchUserInfo(): Resource<UserInfoResponse> {
        return repository.callFetchUserInfo()
    }

    override fun getDbUserInfoLiveData(): LiveData<UserInfoEntity> {
        return dataSource.getDbUserInfoLiveData()
    }

}
