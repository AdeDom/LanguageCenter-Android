package com.lc.library.presentation.usecase

import com.lc.library.data.repository.Resource
import com.lc.server.models.request.EditProfileRequest
import com.lc.server.models.response.BaseResponse

interface EditUserInfoUseCase {

    suspend fun callEditProfile(editProfileRequest: EditProfileRequest): Resource<BaseResponse>

}
