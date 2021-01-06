package com.lc.library.data.network.source

import androidx.lifecycle.LiveData
import com.lc.library.data.db.AppDatabase
import com.lc.library.data.db.entities.UserInfoEntity
import com.lc.server.models.request.*
import com.lc.server.models.response.*

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

    override suspend fun callAddAlgorithm(addAlgorithmRequest: AddAlgorithmRequest): BaseResponse {
        return provider.getLanguageCenterDataSource().callAddAlgorithm(addAlgorithmRequest)
    }

    override suspend fun callAddChatGroupNew(addChatGroupNewRequest: AddChatGroupNewRequest): BaseResponse {
        return provider.getLanguageCenterDataSource().callAddChatGroupNew(addChatGroupNewRequest)
    }

    override suspend fun callAddChatGroup(addChatGroupRequest: AddChatGroupRequest): BaseResponse {
        return provider.getLanguageCenterDataSource().callAddChatGroup(addChatGroupRequest)
    }

    override suspend fun callFetchChatGroup(): FetchChatGroupResponse {
        return provider.getLanguageCenterDataSource().callFetchChatGroup()
    }

    override suspend fun callFetchChatGroupDetail(chatGroupId: Int?): FetchChatGroupDetailResponse {
        return provider.getLanguageCenterDataSource().callFetchChatGroupDetail(chatGroupId)
    }

}
