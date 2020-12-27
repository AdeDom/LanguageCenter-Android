package com.lc.library.presentation.usecase

import com.lc.library.data.repository.Resource
import com.lc.server.models.request.SignInRequest
import com.lc.server.models.response.SignInResponse

interface SignInUseCase {
    suspend operator fun invoke(request: SignInRequest): Resource<SignInResponse>
}
