package com.lc.library.presentation.usecase

import com.lc.library.data.repository.Resource
import com.lc.server.models.response.BaseResponse

interface ValidateTokenUseCase {
    suspend operator fun invoke(): Resource<BaseResponse>
}
