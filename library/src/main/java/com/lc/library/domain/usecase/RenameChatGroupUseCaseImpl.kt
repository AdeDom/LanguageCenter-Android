package com.lc.library.domain.usecase

import com.lc.library.data.repository.Resource
import com.lc.library.domain.repository.LanguageCenterRepository
import com.lc.library.presentation.usecase.RenameChatGroupUseCase
import com.lc.server.models.request.RenameChatGroupRequest
import com.lc.server.models.response.BaseResponse

class RenameChatGroupUseCaseImpl(
    private val repository: LanguageCenterRepository,
) : RenameChatGroupUseCase {
    override suspend fun invoke(renameChatGroupRequest: RenameChatGroupRequest): Resource<BaseResponse> {
        return repository.callRenameChatGroup(renameChatGroupRequest)
    }
}
