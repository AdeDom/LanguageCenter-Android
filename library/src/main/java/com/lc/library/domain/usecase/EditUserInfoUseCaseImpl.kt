package com.lc.library.domain.usecase

import com.lc.library.data.repository.Resource
import com.lc.library.domain.repository.LanguageCenterRepository
import com.lc.library.presentation.usecase.EditUserInfoUseCase
import com.lc.server.models.request.EditProfileRequest
import com.lc.server.models.response.BaseResponse

class EditUserInfoUseCaseImpl(
    private val repository: LanguageCenterRepository,
) : EditUserInfoUseCase {

    override suspend fun callEditProfile(editProfileRequest: EditProfileRequest): Resource<BaseResponse> {
        return repository.callEditProfile(editProfileRequest)
    }

}
