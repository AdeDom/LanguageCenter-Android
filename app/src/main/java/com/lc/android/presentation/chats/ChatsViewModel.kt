package com.lc.android.presentation.chats

import androidx.lifecycle.LiveData
import com.lc.android.base.BaseViewModel
import com.lc.library.data.db.entities.ChatListEntity
import com.lc.library.presentation.usecase.GetChatListUseCase

class ChatsViewModel(
    private val useCase: GetChatListUseCase,
) : BaseViewModel<ChatsViewState>(ChatsViewState) {

    val getDbChatListLiveData: LiveData<List<ChatListEntity>>
        get() = useCase()

}
