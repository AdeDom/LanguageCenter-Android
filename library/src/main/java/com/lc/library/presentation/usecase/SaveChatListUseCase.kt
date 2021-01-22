package com.lc.library.presentation.usecase

import com.lc.library.data.db.entities.ChatListEntity

interface SaveChatListUseCase {
    suspend operator fun invoke(chatListEntity: ChatListEntity)
}
