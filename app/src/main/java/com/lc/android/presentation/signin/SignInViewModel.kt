package com.lc.android.presentation.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lc.android.base.BaseViewModel
import com.lc.library.data.repository.Resource
import com.lc.library.presentation.usecase.SignInUseCase
import com.lc.library.presentation.usecase.SignOutUseCase
import com.lc.server.models.request.SignInRequest
import com.lc.server.models.response.SignInResponse
import kotlinx.coroutines.launch

class SignInViewModel(
    private val signInUseCase: SignInUseCase,
    private val signOutUseCase: SignOutUseCase,
) : BaseViewModel<SignInViewState>(SignInViewState()) {

    private val _signInEvent = MutableLiveData<SignInResponse>()
    val signInEvent: LiveData<SignInResponse>
        get() = _signInEvent

    fun callSignIn(serverAuthCode: String?) {
        launch {
            setState { copy(isLoading = true, isClickable = false) }

            val request = SignInRequest(serverAuthCode)
            when (val response = signInUseCase(request)) {
                is Resource.Success -> _signInEvent.value = response.data
                is Resource.Error -> setError(response.throwable)
            }

            setState { copy(isLoading = false, isClickable = true) }
        }
    }

    fun signOut() {
        launch {
            signOutUseCase()
        }
    }

}
