package com.lc.library.domain.usecase

import com.lc.library.data.db.entities.ChatListEntity
import com.lc.library.domain.repository.LanguageCenterRepository
import com.lc.library.presentation.usecase.SaveChatListUseCase

class SaveChatListUseCaseImpl(
    private val repository: LanguageCenterRepository,
) : SaveChatListUseCase {
    override suspend fun invoke(chatListEntity: ChatListEntity) {
        repository.saveChatListEntity(chatListEntity)
    }
}
