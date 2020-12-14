package com.lc.android.presentation.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lc.android.base.BaseViewModel
import com.lc.library.data.repository.Resource
import com.lc.library.presentation.SignInUseCase
import com.lc.server.models.request.SignInRequest
import com.lc.server.models.response.SignInResponse
import kotlinx.coroutines.launch

class SignInViewModel(
    private val useCase: SignInUseCase,
) : BaseViewModel<SignInViewState>(SignInViewState()) {

    private val _signInEvent = MutableLiveData<SignInResponse>()
    val signInEvent: LiveData<SignInResponse>
        get() = _signInEvent

    fun callSignIn(serverAuthCode: String?) {
        launch {
            setState { copy(isLoading = true, isClickable = false) }

            val request = SignInRequest(serverAuthCode)
            when (val response = useCase(request)) {
                is Resource.Success -> {
                    _signInEvent.value = response.data
                }
                is Resource.Error -> {
                    setError(response.throwable)
                }
            }

            setState { copy(isLoading = false, isClickable = true) }
        }
    }

}
