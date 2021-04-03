package com.lc.library.presentation.usecase

import com.lc.library.data.db.entities.ChatListEntity
import kotlinx.coroutines.flow.Flow

interface GetChatListUseCase {

    fun getDbChatListFlow(): Flow<List<ChatListEntity>>

    suspend fun getDbChatListBySearch(search: String?): List<ChatListEntity>?

}
