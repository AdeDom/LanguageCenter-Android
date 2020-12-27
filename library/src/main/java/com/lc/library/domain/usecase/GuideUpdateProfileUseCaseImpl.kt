package com.lc.library.domain.usecase

import com.lc.library.data.repository.Resource
import com.lc.library.domain.repository.LanguageCenterRepository
import com.lc.library.presentation.usecase.GuideUpdateProfileUseCase
import com.lc.server.models.request.GuideUpdateProfileRequest
import com.lc.server.models.response.BaseResponse

class GuideUpdateProfileUseCaseImpl(
    private val repository: LanguageCenterRepository,
) : GuideUpdateProfileUseCase {
    override suspend fun invoke(guideUpdateProfileRequest: GuideUpdateProfileRequest): Resource<BaseResponse> {
        return repository.callGuideUpdateProfile(guideUpdateProfileRequest)
    }
}
