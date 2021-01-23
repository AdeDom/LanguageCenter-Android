package com.lc.library.presentation.usecase

import com.lc.library.data.repository.Resource
import com.lc.server.models.request.SendMessageRequest
import com.lc.server.models.response.SendMessageResponse

interface SendMessageUseCase {
    suspend operator fun invoke(sendMessageRequest: SendMessageRequest): Resource<SendMessageResponse>?
}
