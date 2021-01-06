package com.lc.library.presentation.usecase

import com.lc.library.data.repository.Resource
import com.lc.server.models.response.FetchChatGroupResponse

interface FetchChatGroup {
    suspend operator fun invoke(): Resource<FetchChatGroupResponse>
}
