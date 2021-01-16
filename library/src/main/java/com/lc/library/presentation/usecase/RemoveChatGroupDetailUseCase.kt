package com.lc.library.presentation.usecase

import com.lc.library.data.repository.Resource
import com.lc.server.models.request.RemoveChatGroupDetailRequest
import com.lc.server.models.response.BaseResponse

interface RemoveChatGroupDetailUseCase {
    suspend operator fun invoke(removeChatGroupDetailRequest: RemoveChatGroupDetailRequest): Resource<BaseResponse>
}
