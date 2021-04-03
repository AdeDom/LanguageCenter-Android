package com.lc.library.presentation.usecase

import com.lc.library.data.db.entities.UserInfoEntity
import kotlinx.coroutines.flow.Flow

interface GetUserInfoUseCase {

    fun getDbUserInfoFlow(): Flow<UserInfoEntity>

    suspend fun getDbUserInfo(): UserInfoEntity?

}
