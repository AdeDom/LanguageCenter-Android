package com.lc.library.domain.usecase

import com.lc.library.data.repository.Resource
import com.lc.library.domain.repository.LanguageCenterRepository
import com.lc.library.presentation.usecase.RemoveChatGroupUseCase
import com.lc.server.models.response.BaseResponse

class RemoveChatGroupUseCaseImpl(
    private val repository: LanguageCenterRepository,
) : RemoveChatGroupUseCase {
    override suspend fun invoke(chatGroupId: Int?): Resource<BaseResponse> {
        return repository.callRemoveChatGroup(chatGroupId)
    }
}
