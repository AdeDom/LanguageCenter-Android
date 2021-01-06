package com.lc.library.domain.repository

import com.lc.library.data.repository.Resource
import com.lc.server.models.request.*
import com.lc.server.models.response.BaseResponse
import com.lc.server.models.response.FetchCommunityResponse
import com.lc.server.models.response.SignInResponse
import com.lc.server.models.response.UserInfoResponse

interface LanguageCenterRepository {

    suspend fun callSignIn(request: SignInRequest): Resource<SignInResponse>

    suspend fun callFetchUserInfo(): Resource<UserInfoResponse>

    suspend fun signOut()

    suspend fun callGuideUpdateProfile(guideUpdateProfileRequest: GuideUpdateProfileRequest): Resource<BaseResponse>

    suspend fun callEditProfile(editProfileRequest: EditProfileRequest): Resource<BaseResponse>

    suspend fun callEditLocale(editLocaleRequest: EditLocaleRequest): Resource<BaseResponse>

    suspend fun callFetchCommunity(): Resource<FetchCommunityResponse>

    suspend fun callAddAlgorithm(addAlgorithmRequest: AddAlgorithmRequest): Resource<BaseResponse>

    suspend fun callAddChatGroupNew(addChatGroupNewRequest: AddChatGroupNewRequest): Resource<BaseResponse>

}
