package com.lc.library.presentation.usecase

import com.lc.library.data.repository.Resource
import com.lc.server.models.response.BaseResponse

interface ReadMessagesUseCase {
    suspend operator fun invoke(readUserId: String?): Resource<BaseResponse>
}
