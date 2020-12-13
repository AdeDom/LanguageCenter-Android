package com.lc.android.presentation.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lc.android.base.BaseViewModel
import com.lc.library.presentation.SignInUseCase
import com.lc.library.sharedpreference.pref.PreferenceAuth
import com.lc.server.models.request.SignInRequest
import com.lc.server.models.response.SignInResponse
import kotlinx.coroutines.launch

class SignInViewModel(
    private val pref:PreferenceAuth,
    private val useCase: SignInUseCase,
) : BaseViewModel<SignInViewState>(SignInViewState()) {

    private val _signInEvent = MutableLiveData<SignInResponse>()
    val signInEvent: LiveData<SignInResponse>
        get() = _signInEvent

    fun signIn(serverAuthCode: String?) {
        launch {
            setState { copy(loading = true) }

            val request = SignInRequest(serverAuthCode)
            val response = useCase(request)
            if (response?.success == true) {
                pref.accessToken = response.token?.accessToken.orEmpty()
                pref.refreshToken = response.token?.refreshToken.orEmpty()
            }
            _signInEvent.value = response

            setState { copy(loading = false) }
        }
    }

}
