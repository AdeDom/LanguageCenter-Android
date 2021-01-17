package com.lc.android.presentation.addchatgroupdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lc.android.base.BaseViewModel
import com.lc.library.data.db.entities.AddChatGroupDetailEntity
import com.lc.library.data.repository.Resource
import com.lc.library.presentation.usecase.AddChatGroupDetailUseCase
import com.lc.library.presentation.usecase.GetAddChatGroupDetailUseCase
import com.lc.server.models.request.AddChatGroupDetailRequest
import kotlinx.coroutines.launch

class AddChatGroupDetailViewModel(
    private val getAddChatGroupDetailUseCase: GetAddChatGroupDetailUseCase,
    private val addChatGroupDetailUseCase: AddChatGroupDetailUseCase,
) : BaseViewModel<AddChatGroupDetailViewState>(AddChatGroupDetailViewState()) {

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

    fun callAddChatGroupDetail(
        chatGroupId: Int,
        userId: String,
        addChatGroupDetailEntity: AddChatGroupDetailEntity,
    ) {
        launch {
            setState { copy(isLoading = true, isClickable = false) }

            val request = AddChatGroupDetailRequest(
                chatGroupId = chatGroupId,
                userId = userId,
            )
            when (val resource = addChatGroupDetailUseCase(request, addChatGroupDetailEntity)) {
                is Resource.Success -> getDbAddChatGroupDetailBySearch()
                is Resource.Error -> setError(resource.throwable)
            }

            setState { copy(isLoading = false, isClickable = true) }
        }
    }

}
