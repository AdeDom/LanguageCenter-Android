package com.lc.library.presentation.usecase

import com.lc.library.data.repository.Resource
import com.lc.server.models.request.RenameChatGroupRequest
import com.lc.server.models.response.BaseResponse

interface RenameChatGroupUseCase {
    suspend operator fun invoke(renameChatGroupRequest: RenameChatGroupRequest): Resource<BaseResponse>
}
