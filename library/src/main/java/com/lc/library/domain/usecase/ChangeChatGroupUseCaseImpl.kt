package com.lc.library.domain.usecase

import com.lc.library.data.repository.Resource
import com.lc.library.domain.repository.LanguageCenterRepository
import com.lc.library.presentation.usecase.ChangeChatGroupUseCase
import com.lc.server.models.request.ChangeChatGroupRequest
import com.lc.server.models.response.BaseResponse

class ChangeChatGroupUseCaseImpl(
    private val repository: LanguageCenterRepository,
) : ChangeChatGroupUseCase {
    override suspend fun invoke(changeChatGroupRequest: ChangeChatGroupRequest): Resource<BaseResponse> {
        return repository.callChangeChatGroup(changeChatGroupRequest)
    }
}
