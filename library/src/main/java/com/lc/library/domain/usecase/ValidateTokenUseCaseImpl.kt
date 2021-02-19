package com.lc.library.domain.usecase

import com.lc.library.data.repository.Resource
import com.lc.library.domain.repository.LanguageCenterRepository
import com.lc.library.presentation.usecase.ValidateTokenUseCase
import com.lc.server.models.response.BaseResponse

class ValidateTokenUseCaseImpl(
    private val repository: LanguageCenterRepository,
) : ValidateTokenUseCase {
    override suspend fun invoke(): Resource<BaseResponse> {
        return repository.callValidateToken()
    }
}
