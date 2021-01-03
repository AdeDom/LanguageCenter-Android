package com.lc.library.domain.repository

import com.lc.library.data.repository.Resource
import com.lc.server.models.request.EditLocaleRequest
import com.lc.server.models.request.EditProfileRequest
import com.lc.server.models.request.GuideUpdateProfileRequest
import com.lc.server.models.request.SignInRequest
import com.lc.server.models.response.BaseResponse
import com.lc.server.models.response.SignInResponse
import com.lc.server.models.response.UserInfoResponse

interface LanguageCenterRepository {

    suspend fun callSignIn(request: SignInRequest): Resource<SignInResponse>

    suspend fun callFetchUserInfo(): Resource<UserInfoResponse>

    suspend fun signOut()

    suspend fun callGuideUpdateProfile(guideUpdateProfileRequest: GuideUpdateProfileRequest): Resource<BaseResponse>

    suspend fun callEditProfile(editProfileRequest: EditProfileRequest): Resource<BaseResponse>

    suspend fun callEditLocale(editLocaleRequest: EditLocaleRequest): Resource<BaseResponse>

}
