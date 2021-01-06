package com.lc.library.presentation.usecase

import com.lc.library.data.repository.Resource
import com.lc.server.models.request.AddAlgorithmRequest
import com.lc.server.models.response.BaseResponse

interface AddAlgorithmUseCase {
    suspend operator fun invoke(addAlgorithmRequest: AddAlgorithmRequest): Resource<BaseResponse>
}
