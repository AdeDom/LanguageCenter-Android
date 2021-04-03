package com.lc.android.presentation.chats

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.lc.android.base.BaseViewModel
import com.lc.library.data.db.entities.ChatListEntity
import com.lc.library.presentation.usecase.GetChatListUseCase
import kotlinx.coroutines.launch

class ChatsViewModel(
    private val useCase: GetChatListUseCase,
) : BaseViewModel<ChatsViewState>(ChatsViewState()) {

    private val _getDbChatListBySearch = MutableLiveData<List<ChatListEntity>>()
    val getDbChatListBySearch: LiveData<List<ChatListEntity>>
        get() = _getDbChatListBySearch

    val getDbChatListLiveData: LiveData<List<ChatListEntity>>
        get() = useCase.getDbChatListFlow().asLiveData()

    fun setStateSearch(search: String) {
        setState { copy(search = search) }
    }

    fun getDbChatListBySearch() {
        launch {
            _getDbChatListBySearch.value = useCase.getDbChatListBySearch(state.value?.search)
        }
    }

}
