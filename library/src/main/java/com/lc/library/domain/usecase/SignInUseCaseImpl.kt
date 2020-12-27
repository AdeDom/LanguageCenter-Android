package com.lc.library.domain.usecase

import com.lc.library.data.repository.Resource
import com.lc.library.domain.repository.LanguageCenterRepository
import com.lc.library.presentation.usecase.SignInUseCase
import com.lc.server.models.request.SignInRequest
import com.lc.server.models.response.SignInResponse

class SignInUseCaseImpl(private val repository: LanguageCenterRepository) : SignInUseCase {
    override suspend fun invoke(request: SignInRequest): Resource<SignInResponse> {
        return repository.callSignIn(request)
    }
}
