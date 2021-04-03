package com.lc.library.domain.usecase

import com.lc.library.data.db.entities.ChatListEntity
import com.lc.library.data.network.source.LanguageCenterDataSource
import com.lc.library.presentation.usecase.GetChatListUseCase
import kotlinx.coroutines.flow.Flow

class GetChatListUseCaseImpl(
    private val dataSource: LanguageCenterDataSource,
) : GetChatListUseCase {

    override fun getDbChatListFlow(): Flow<List<ChatListEntity>> {
        return dataSource.getDbChatListFlow()
    }

    override suspend fun getDbChatListBySearch(search: String?): List<ChatListEntity>? {
        return dataSource.getDbChatListBySearch(search)
    }

}
