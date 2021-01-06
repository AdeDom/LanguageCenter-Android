package com.lc.android.presentation.chatgroup

import com.lc.android.base.BaseViewModel
import com.lc.library.data.repository.Resource
import com.lc.library.presentation.usecase.FetchChatGroup
import kotlinx.coroutines.launch

class ChatGroupViewModel(
    private val useCase: FetchChatGroup,
) : BaseViewModel<ChatGroupViewState>(ChatGroupViewState()) {

    fun callFetchChatGroup() {
        launch {
            setState { copy(isLoading = true) }

            when (val resource = useCase()) {
                is Resource.Success -> setState { copy(chatGroups = resource.data.chatGroups) }
                is Resource.Error -> setError(resource.throwable)
            }

            setState { copy(isLoading = false) }
        }
    }

}
