package com.lc.library.domain.usecase

import androidx.lifecycle.LiveData
import com.lc.library.data.db.entities.ChatListEntity
import com.lc.library.data.network.source.LanguageCenterDataSource
import com.lc.library.presentation.usecase.GetChatListUseCase

class GetChatListUseCaseImpl(
    private val useCase: LanguageCenterDataSource,
) : GetChatListUseCase {
    override fun invoke(): LiveData<List<ChatListEntity>> {
        return useCase.getDbChatListLiveData()
    }
}
