package com.lc.library.domain.usecase

import androidx.lifecycle.LiveData
import com.lc.library.data.db.entities.UserInfoEntity
import com.lc.library.data.network.source.LanguageCenterDataSource
import com.lc.library.presentation.usecase.GetUserInfoUseCase

class GetUserInfoUseCaseImpl(
    private val dataSource: LanguageCenterDataSource,
) : GetUserInfoUseCase {

    override fun getDbUserInfoLiveData(): LiveData<UserInfoEntity> {
        return dataSource.getDbUserInfoLiveData()
    }

    override suspend fun getDbUserInfo(): UserInfoEntity? {
        return dataSource.getDbUserInfo()
    }

}
