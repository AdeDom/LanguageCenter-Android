package com.lc.library.domain.usecase

import com.lc.library.data.db.entities.UserInfoEntity
import com.lc.library.data.network.source.LanguageCenterDataSource
import com.lc.library.presentation.usecase.GetUserInfoUseCase
import kotlinx.coroutines.flow.Flow

class GetUserInfoUseCaseImpl(
    private val dataSource: LanguageCenterDataSource,
) : GetUserInfoUseCase {

    override fun getDbUserInfoFlow(): Flow<UserInfoEntity> {
        return dataSource.getDbUserInfoFlow()
    }

    override suspend fun getDbUserInfo(): UserInfoEntity? {
        return dataSource.getDbUserInfo()
    }

}
