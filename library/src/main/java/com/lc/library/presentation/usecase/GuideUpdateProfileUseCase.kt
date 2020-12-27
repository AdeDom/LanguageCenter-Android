package com.lc.library.presentation.usecase

import com.lc.library.data.repository.Resource
import com.lc.server.models.request.GuideUpdateProfileRequest
import com.lc.server.models.response.BaseResponse

interface GuideUpdateProfileUseCase {
    suspend operator fun invoke(guideUpdateProfileRequest: GuideUpdateProfileRequest): Resource<BaseResponse>
}
