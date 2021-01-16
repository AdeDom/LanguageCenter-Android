package com.lc.library.domain.usecase

import com.lc.library.data.repository.Resource
import com.lc.library.domain.repository.LanguageCenterRepository
import com.lc.library.presentation.usecase.RemoveChatGroupDetailUseCase
import com.lc.server.models.request.RemoveChatGroupDetailRequest
import com.lc.server.models.response.BaseResponse

class RemoveChatGroupDetailUseCaseImpl(
    private val repository: LanguageCenterRepository,
) : RemoveChatGroupDetailUseCase {
    override suspend fun invoke(removeChatGroupDetailRequest: RemoveChatGroupDetailRequest): Resource<BaseResponse> {
        return repository.callRemoveChatGroupDetail(removeChatGroupDetailRequest)
    }
}
