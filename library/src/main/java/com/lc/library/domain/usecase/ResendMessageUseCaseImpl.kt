package com.lc.library.domain.usecase

import com.lc.library.data.repository.Resource
import com.lc.library.domain.repository.LanguageCenterRepository
import com.lc.library.presentation.usecase.ResendMessageUseCase
import com.lc.server.models.request.ResendMessageRequest
import com.lc.server.models.response.BaseResponse

class ResendMessageUseCaseImpl(
    private val repository: LanguageCenterRepository,
) : ResendMessageUseCase {
    override suspend fun invoke(resendMessageRequest: ResendMessageRequest): Resource<BaseResponse>? {
        return repository.callResendMessage(resendMessageRequest)
    }
}
