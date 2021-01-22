package com.lc.library.domain.usecase

import com.lc.library.data.repository.Resource
import com.lc.library.domain.repository.LanguageCenterRepository
import com.lc.library.presentation.usecase.ReadMessagesUseCase
import com.lc.server.models.response.BaseResponse

class ReadMessagesUseCaseImpl(
    private val repository: LanguageCenterRepository,
) : ReadMessagesUseCase {
    override suspend fun invoke(readUserId: String?): Resource<BaseResponse> {
        return repository.callReadMessages(readUserId)
    }
}
