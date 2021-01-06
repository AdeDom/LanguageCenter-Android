package com.lc.android.presentation.community

import com.lc.android.base.BaseViewModel
import com.lc.library.data.repository.Resource
import com.lc.library.presentation.usecase.FetchCommunityUseCase
import kotlinx.coroutines.launch

class CommunityViewModel(
    private val useCase: FetchCommunityUseCase,
) : BaseViewModel<CommunityViewState>(CommunityViewState()) {

    fun callFetchCommunity() {
        launch {
            setState { copy(isLoading = true) }

            when (val resource = useCase()) {
                is Resource.Success -> setState { copy(communities = resource.data.communities) }
                is Resource.Error -> setError(resource.throwable)
            }

            setState { copy(isLoading = false) }
        }
    }

}
