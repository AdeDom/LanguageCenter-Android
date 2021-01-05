package com.lc.library.data.network.source

import androidx.lifecycle.LiveData
import com.lc.library.data.db.AppDatabase
import com.lc.library.data.db.entities.UserInfoEntity
import com.lc.server.models.request.EditLocaleRequest
import com.lc.server.models.request.EditProfileRequest
import com.lc.server.models.request.GuideUpdateProfileRequest
import com.lc.server.models.request.SignInRequest
import com.lc.server.models.response.BaseResponse
import com.lc.server.models.response.FetchCommunityResponse
import com.lc.server.models.response.SignInResponse
import com.lc.server.models.response.UserInfoResponse

class LanguageCenterDataSourceImpl(
    private val db: AppDatabase,
    private val provider: DataSourceProvider,
) : LanguageCenterDataSource {

    override suspend fun saveUserInfo(userInfo: UserInfoEntity) {
        return db.getUserInfoDao().saveUserInfo(userInfo)
    }

    override suspend fun getDbUserInfo(): UserInfoEntity? {
        return db.getUserInfoDao().getDbUserInfo()
    }

    override fun getDbUserInfoLiveData(): LiveData<UserInfoEntity> {
        return db.getUserInfoDao().getDbUserInfoLiveData()
    }

    override suspend fun deleteUserInfo() {
        return db.getUserInfoDao().deleteUserInfo()
    }

    override suspend fun callSignIn(request: SignInRequest): SignInResponse {
        return provider.getDataSource().callSignIn(request)
    }

    override suspend fun callFetchUserInfo(): UserInfoResponse {
        return provider.getLanguageCenterDataSource().callFetchUserInfo()
    }

    override suspend fun callGuideUpdateProfile(guideUpdateProfileRequest: GuideUpdateProfileRequest): BaseResponse {
        return provider.getLanguageCenterDataSource()
            .callGuideUpdateProfile(guideUpdateProfileRequest)
    }

    override suspend fun callEditProfile(editProfileRequest: EditProfileRequest): BaseResponse {
        return provider.getLanguageCenterDataSource().callEditProfile(editProfileRequest)
    }

    override suspend fun callEditLocale(editLocaleRequest: EditLocaleRequest): BaseResponse {
        return provider.getLanguageCenterDataSource().callEditLocale(editLocaleRequest)
    }

    override suspend fun callFetchCommunity(): FetchCommunityResponse {
        return provider.getLanguageCenterDataSource().callFetchCommunity()
    }

}
