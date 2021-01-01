package com.lc.library.presentation.usecase

import androidx.lifecycle.LiveData
import com.lc.library.data.db.entities.UserInfoEntity

interface GetUserInfoUseCase {

    fun getDbUserInfoLiveData(): LiveData<UserInfoEntity>

    suspend fun getDbUserInfo(): UserInfoEntity?

}
