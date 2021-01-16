package com.lc.library.presentation.usecase

import com.lc.library.data.db.entities.AddChatGroupDetailEntity
import com.lc.library.data.repository.Resource
import com.lc.server.models.response.FetchAddChatGroupDetailResponse

interface GetAddChatGroupDetailUseCase {

    suspend fun callFetchAddChatGroupDetail(): Resource<FetchAddChatGroupDetailResponse>

    suspend fun getDbAddChatGroupDetailBySearch(search: String?): List<AddChatGroupDetailEntity>?

}
