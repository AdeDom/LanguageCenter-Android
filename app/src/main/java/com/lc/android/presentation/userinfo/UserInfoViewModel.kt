package com.lc.android.presentation.userinfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lc.android.base.BaseViewModel
import com.lc.library.data.repository.Resource
import com.lc.library.presentation.usecase.AddAlgorithmUseCase
import com.lc.library.presentation.usecase.AddChatGroupNewUseCase
import com.lc.server.models.request.AddAlgorithmRequest
import com.lc.server.models.request.AddChatGroupNewRequest
import com.lc.server.models.response.BaseResponse
import kotlinx.coroutines.launch

class UserInfoViewModel(
    private val addAlgorithmUseCase: AddAlgorithmUseCase,
    private val addChatGroupNewUseCase: AddChatGroupNewUseCase,
) : BaseViewModel<UserInfoViewState>(UserInfoViewState()) {

    private val _addChatGroupNewEvent = MutableLiveData<BaseResponse>()
    val addChatGroupNewEvent: LiveData<BaseResponse>
        get() = _addChatGroupNewEvent

    fun callAddAlgorithm(algorithm: String?) {
        launch {
            setState { copy(isLoading = true, isClickable = false) }

            val request = AddAlgorithmRequest(algorithm)
            when (val resource = addAlgorithmUseCase(request)) {
                is Resource.Error -> setError(resource.throwable)
            }

            setState { copy(isLoading = false, isClickable = true) }
        }
    }

    fun callAddChatGroupNew(userId: String?) {
        launch {
            setState { copy(isLoading = true, isClickable = false) }

            val request = AddChatGroupNewRequest(userId)
            when (val resource = addChatGroupNewUseCase(request)) {
                is Resource.Success -> _addChatGroupNewEvent.value = resource.data
                is Resource.Error -> setError(resource.throwable)
            }

            setState { copy(isLoading = false, isClickable = true) }
        }
    }

}
