package com.lc.library.presentation.usecase

import com.lc.library.data.repository.Resource
import com.lc.server.models.request.ChangeChatGroupRequest
import com.lc.server.models.response.BaseResponse

interface ChangeChatGroupUseCase {
    suspend operator fun invoke(changeChatGroupRequest: ChangeChatGroupRequest): Resource<BaseResponse>
}
