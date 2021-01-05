package com.lc.library.data.network.source

import androidx.lifecycle.LiveData
import com.lc.library.data.db.entities.UserInfoEntity
import com.lc.server.models.request.EditLocaleRequest
import com.lc.server.models.request.EditProfileRequest
import com.lc.server.models.request.GuideUpdateProfileRequest
import com.lc.server.models.request.SignInRequest
import com.lc.server.models.response.BaseResponse
import com.lc.server.models.response.FetchCommunityResponse
import com.lc.server.models.response.SignInResponse
import com.lc.server.models.response.UserInfoResponse

interface LanguageCenterDataSource {

    suspend fun saveUserInfo(userInfo: UserInfoEntity)

    suspend fun getDbUserInfo(): UserInfoEntity?

    fun getDbUserInfoLiveData(): LiveData<UserInfoEntity>

    suspend fun deleteUserInfo()

    suspend fun callSignIn(request: SignInRequest): SignInResponse

    suspend fun callFetchUserInfo(): UserInfoResponse

    suspend fun callGuideUpdateProfile(guideUpdateProfileRequest: GuideUpdateProfileRequest): BaseResponse

    suspend fun callEditProfile(editProfileRequest: EditProfileRequest): BaseResponse

    suspend fun callEditLocale(editLocaleRequest: EditLocaleRequest): BaseResponse

    suspend fun callFetchCommunity(): FetchCommunityResponse

}
