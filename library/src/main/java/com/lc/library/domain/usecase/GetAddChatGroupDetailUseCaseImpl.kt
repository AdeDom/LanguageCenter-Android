package com.lc.library.domain.usecase

import com.lc.library.data.db.entities.AddChatGroupDetailEntity
import com.lc.library.data.network.source.LanguageCenterDataSource
import com.lc.library.data.repository.Resource
import com.lc.library.domain.repository.LanguageCenterRepository
import com.lc.library.presentation.usecase.GetAddChatGroupDetailUseCase
import com.lc.server.models.response.FetchAddChatGroupDetailResponse

class GetAddChatGroupDetailUseCaseImpl(
    private val repository: LanguageCenterRepository,
    private val dataSource: LanguageCenterDataSource,
) : GetAddChatGroupDetailUseCase {

    override suspend fun callFetchAddChatGroupDetail(): Resource<FetchAddChatGroupDetailResponse> {
        return repository.callFetchAddChatGroupDetail()
    }

    override suspend fun getDbAddChatGroupDetailBySearch(search: String?): List<AddChatGroupDetailEntity>? {
        return dataSource.getDbAddChatGroupDetailBySearch(search)
    }

}
