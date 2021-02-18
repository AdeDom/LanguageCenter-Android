package com.lc.android.presentation.addfriendgroupdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lc.android.base.BaseViewModel
import com.lc.library.data.db.entities.AddChatGroupDetailEntity
import com.lc.library.data.repository.Resource
import com.lc.library.presentation.usecase.GetAddChatGroupDetailUseCase
import kotlinx.coroutines.launch

class AddFriendGroupDetailViewModel(
    private val getAddChatGroupDetailUseCase: GetAddChatGroupDetailUseCase,
) : BaseViewModel<AddFriendGroupDetailViewState>(AddFriendGroupDetailViewState()) {

    private val _getDbAddChatGroupDetail = MutableLiveData<List<AddChatGroupDetailEntity>>()
    val getDbAddChatGroupDetail: LiveData<List<AddChatGroupDetailEntity>>
        get() = _getDbAddChatGroupDetail

    fun callFetchAddChatGroupDetail() {
        launch {
            setState { copy(isLoading = true, isClickable = false) }

            when (val resource = getAddChatGroupDetailUseCase.callFetchAddChatGroupDetail()) {
                is Resource.Success -> getDbAddChatGroupDetailBySearch()
                is Resource.Error -> setError(resource.throwable)
            }

            setState { copy(isLoading = false, isClickable = true) }
        }
    }

    fun setStateSearch(search: String) {
        setState { copy(search = search) }
    }

    fun getDbAddChatGroupDetailBySearch() {
        launch {
            _getDbAddChatGroupDetail.value = getAddChatGroupDetailUseCase
                .getDbAddChatGroupDetailBySearch(state.value?.search)
        }
    }

}
