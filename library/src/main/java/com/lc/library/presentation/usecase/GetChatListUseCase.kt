package com.lc.library.presentation.usecase

import androidx.lifecycle.LiveData
import com.lc.library.data.db.entities.ChatListEntity

interface GetChatListUseCase {
    operator fun invoke(): LiveData<List<ChatListEntity>>
}
