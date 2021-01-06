package com.lc.library.presentation.usecase

import com.lc.library.data.repository.Resource
import com.lc.server.models.request.AddChatGroupRequest
import com.lc.server.models.response.BaseResponse

interface AddChatGroupUseCase {
    suspend operator fun invoke(addChatGroupRequest: AddChatGroupRequest): Resource<BaseResponse>
}
