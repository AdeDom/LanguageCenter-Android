package com.lc.android.presentation.addchatgroupdetail

import com.lc.android.base.BaseViewModel
import com.lc.library.data.repository.Resource
import com.lc.library.presentation.usecase.FetchAddChatGroupDetailUseCase
import kotlinx.coroutines.launch

class AddChatGroupDetailViewModel(
    private val fetchAddChatGroupDetailUseCase: FetchAddChatGroupDetailUseCase,
) : BaseViewModel<AddChatGroupDetailViewState>(AddChatGroupDetailViewState()) {

    fun callFetchAddChatGroupDetail() {
        launch {
            setState { copy(isLoading = true) }

            when (val resource = fetchAddChatGroupDetailUseCase()) {
                is Resource.Success -> setState { copy(addChatGroupDetailList = resource.data.addChatGroupDetailList) }
                is Resource.Error -> setError(resource.throwable)
            }

            setState { copy(isLoading = false) }
        }
    }

}
