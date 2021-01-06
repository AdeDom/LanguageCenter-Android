package com.lc.library.domain.usecase

import com.lc.library.data.repository.Resource
import com.lc.library.domain.repository.LanguageCenterRepository
import com.lc.library.presentation.usecase.AddAlgorithmUseCase
import com.lc.server.models.request.AddAlgorithmRequest
import com.lc.server.models.response.BaseResponse

class AddAlgorithmUseCaseImpl(
    private val repository: LanguageCenterRepository,
) : AddAlgorithmUseCase {
    override suspend fun invoke(addAlgorithmRequest: AddAlgorithmRequest): Resource<BaseResponse> {
        return repository.callAddAlgorithm(addAlgorithmRequest)
    }
}
