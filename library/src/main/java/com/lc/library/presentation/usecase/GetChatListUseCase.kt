package com.lc.library.presentation.usecase

import androidx.lifecycle.LiveData
import com.lc.library.data.db.entities.ChatListEntity

interface GetChatListUseCase {

    fun getDbChatListLiveData(): LiveData<List<ChatListEntity>>

    suspend fun getDbChatListBySearch(search: String?): List<ChatListEntity>?

}
