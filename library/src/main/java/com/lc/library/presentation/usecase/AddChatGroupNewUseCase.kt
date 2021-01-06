package com.lc.library.presentation.usecase

import com.lc.library.data.repository.Resource
import com.lc.server.models.request.AddChatGroupNewRequest
import com.lc.server.models.response.BaseResponse

interface AddChatGroupNewUseCase {
    suspend operator fun invoke(addChatGroupNewRequest: AddChatGroupNewRequest): Resource<BaseResponse>
}
