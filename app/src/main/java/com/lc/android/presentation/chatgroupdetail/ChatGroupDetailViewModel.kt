package com.lc.android.presentation.chatgroupdetail

import com.lc.android.base.BaseViewModel
import com.lc.library.data.repository.Resource
import com.lc.library.presentation.usecase.FetchChatGroupDetailUseCase
import kotlinx.coroutines.launch

class ChatGroupDetailViewModel(
    private val useCase: FetchChatGroupDetailUseCase,
) : BaseViewModel<ChatGroupDetailViewState>(ChatGroupDetailViewState()) {

    fun callFetchChatGroupDetail(chatGroupId: Int?) {
        launch {
            setState { copy(isLoading = true) }

            when (val resource = useCase(chatGroupId)) {
                is Resource.Success -> setState { copy(chatGroupDetails = resource.data.chatGroupDetails) }
                is Resource.Error -> setError(resource.throwable)
            }

            setState { copy(isLoading = false) }
        }
    }

}
