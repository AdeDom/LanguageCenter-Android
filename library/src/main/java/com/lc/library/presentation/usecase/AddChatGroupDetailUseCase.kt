package com.lc.library.presentation.usecase

import com.lc.library.data.db.entities.AddChatGroupDetailEntity
import com.lc.library.data.repository.Resource
import com.lc.server.models.request.AddChatGroupDetailRequest
import com.lc.server.models.response.BaseResponse

interface AddChatGroupDetailUseCase {
    suspend operator fun invoke(
        addChatGroupDetailRequest: AddChatGroupDetailRequest,
        addChatGroupDetailEntity: AddChatGroupDetailEntity,
    ): Resource<BaseResponse>
}
