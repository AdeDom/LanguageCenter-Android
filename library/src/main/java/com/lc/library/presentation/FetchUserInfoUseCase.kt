package com.lc.library.presentation

import com.lc.library.data.repository.Resource
import com.lc.server.models.response.UserInfoResponse

interface FetchUserInfoUseCase {
    suspend operator fun invoke(): Resource<UserInfoResponse>
}
