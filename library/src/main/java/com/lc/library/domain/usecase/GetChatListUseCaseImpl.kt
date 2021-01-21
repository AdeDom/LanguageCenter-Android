package com.lc.library.domain.usecase

import androidx.lifecycle.LiveData
import com.lc.library.data.db.entities.ChatListEntity
import com.lc.library.data.network.source.LanguageCenterDataSource
import com.lc.library.presentation.usecase.GetChatListUseCase

class GetChatListUseCaseImpl(
    private val dataSource: LanguageCenterDataSource,
) : GetChatListUseCase {

    override fun getDbChatListLiveData(): LiveData<List<ChatListEntity>> {
        return dataSource.getDbChatListLiveData()
    }

    override suspend fun getDbChatListBySearch(search: String?): List<ChatListEntity>? {
        return dataSource.getDbChatListBySearch(search)
    }

}
