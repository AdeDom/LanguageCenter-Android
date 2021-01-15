package com.lc.android.presentation.addchatgroupdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lc.android.base.BaseViewModel
import com.lc.library.data.db.entities.AddChatGroupDetailEntity
import com.lc.library.data.repository.Resource
import com.lc.library.presentation.usecase.GetAddChatGroupDetailUseCase
import kotlinx.coroutines.launch

class AddChatGroupDetailViewModel(
    private val getAddChatGroupDetailUseCase: GetAddChatGroupDetailUseCase,
) : BaseViewModel<AddChatGroupDetailViewState>(AddChatGroupDetailViewState()) {

    private val _getDbAddChatGroupDetail = MutableLiveData<List<AddChatGroupDetailEntity>>()
    val getDbAddChatGroupDetail: LiveData<List<AddChatGroupDetailEntity>>
        get() = _getDbAddChatGroupDetail

    fun callFetchAddChatGroupDetail() {
        launch {
            setState { copy(isLoading = true) }

            when (val resource = getAddChatGroupDetailUseCase.callFetchAddChatGroupDetail()) {
                is Resource.Success -> getDbAddChatGroupDetail()
                is Resource.Error -> setError(resource.throwable)
            }

            setState { copy(isLoading = false) }
        }
    }

    fun setStateSearch(search: String) {
        setState { copy(search = search) }
    }

    fun getDbAddChatGroupDetail() {
        launch {
            _getDbAddChatGroupDetail.value = getAddChatGroupDetailUseCase
                .getDbAddChatGroupDetail(state.value?.search)
        }
    }

}
