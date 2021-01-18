package com.lc.library.domain.usecase

import com.lc.library.data.repository.Resource
import com.lc.library.domain.repository.LanguageCenterRepository
import com.lc.library.presentation.usecase.SendMessageUseCase
import com.lc.server.models.request.SendMessageRequest
import com.lc.server.models.response.BaseResponse

class SendMessageUseCaseImpl(
    private val repository: LanguageCenterRepository,
) : SendMessageUseCase {
    override suspend fun invoke(sendMessageRequest: SendMessageRequest): Resource<BaseResponse> {
        return repository.callSendMessage(sendMessageRequest)
    }
}
