package com.lc.library.domain.usecase

import com.lc.library.data.repository.Resource
import com.lc.library.domain.repository.LanguageCenterRepository
import com.lc.library.presentation.usecase.AddChatGroupUseCase
import com.lc.server.models.request.AddChatGroupRequest
import com.lc.server.models.response.BaseResponse

class AddChatGroupUseCaseImpl(
    private val repository: LanguageCenterRepository
) : AddChatGroupUseCase {
    override suspend fun invoke(addChatGroupRequest: AddChatGroupRequest): Resource<BaseResponse> {
        return repository.callAddChatGroup(addChatGroupRequest)
    }
}
