package com.lc.android.presentation.addchatgroup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lc.android.base.BaseViewModel
import com.lc.library.data.repository.Resource
import com.lc.library.presentation.usecase.AddChatGroupUseCase
import com.lc.server.models.request.AddChatGroupRequest
import com.lc.server.models.response.BaseResponse
import kotlinx.coroutines.launch

class AddChatGroupViewModel(
    private val useCase: AddChatGroupUseCase,
) : BaseViewModel<AddChatGroupViewState>(AddChatGroupViewState()) {

    private val _addChatGroupEvent = MutableLiveData<BaseResponse>()
    val addChatGroupEvent: LiveData<BaseResponse>
        get() = _addChatGroupEvent

    fun callAddChatGroup() {
        launch {
            setState { copy(isLoading = true, isClickable = false) }

            val request = AddChatGroupRequest(groupName = state.value?.groupName)
            when (val resource = useCase(request)) {
                is Resource.Success -> _addChatGroupEvent.value = resource.data
                is Resource.Error -> setError(resource.throwable)
            }

            setState { copy(isLoading = false, isClickable = true) }
        }
    }

    fun setStateGroupName(groupName: String) {
        setState { copy(groupName = groupName) }
    }

}
