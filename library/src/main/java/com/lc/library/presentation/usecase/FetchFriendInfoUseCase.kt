package com.lc.library.presentation.usecase

import com.lc.library.data.repository.Resource
import com.lc.server.models.request.FetchFriendInfoResponse

interface FetchFriendInfoUseCase {
    suspend operator fun invoke(): Resource<FetchFriendInfoResponse>
}
