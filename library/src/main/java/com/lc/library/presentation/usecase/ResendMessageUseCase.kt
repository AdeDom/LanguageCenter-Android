package com.lc.library.presentation.usecase

import com.lc.library.data.model.ResendMessageRequest
import com.lc.library.data.repository.Resource
import com.lc.server.models.response.BaseResponse

interface ResendMessageUseCase {
    suspend operator fun invoke(resendMessageRequest: ResendMessageRequest): Resource<BaseResponse>
}
