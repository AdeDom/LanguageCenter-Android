package com.lc.library.domain.usecase

import com.lc.library.data.db.entities.AddChatGroupDetailEntity
import com.lc.library.data.repository.Resource
import com.lc.library.domain.repository.LanguageCenterRepository
import com.lc.library.presentation.usecase.AddChatGroupDetailUseCase
import com.lc.server.models.request.AddChatGroupDetailRequest
import com.lc.server.models.response.BaseResponse

class AddChatGroupDetailUseCaseImpl(
    private val repository: LanguageCenterRepository,
) : AddChatGroupDetailUseCase {
    override suspend fun invoke(
        addChatGroupDetailRequest: AddChatGroupDetailRequest,
        addChatGroupDetailEntity: AddChatGroupDetailEntity,
    ): Resource<BaseResponse> {
        return repository.callAddChatGroupDetail(
            addChatGroupDetailRequest,
            addChatGroupDetailEntity,
        )
    }
}
