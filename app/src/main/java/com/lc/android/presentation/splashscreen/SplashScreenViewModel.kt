package com.lc.android.presentation.splashscreen

import com.lc.android.base.BaseViewModel
import com.lc.android.util.SingleLiveEvent
import com.lc.library.data.repository.Resource
import com.lc.library.presentation.usecase.FilePrefUseCase
import com.lc.library.presentation.usecase.ValidateTokenUseCase
import com.lc.server.models.response.BaseResponse
import kotlinx.coroutines.launch

class SplashScreenViewModel(
    private val filePrefUseCase: FilePrefUseCase,
    private val validateTokenUseCase: ValidateTokenUseCase,
) : BaseViewModel<SplashScreenViewState>(SplashScreenViewState()) {

    val validateToken = SingleLiveEvent<BaseResponse>()

    fun isValidateSignIn(): Boolean {
        return filePrefUseCase.getAccessToken().isNotBlank()
    }

    fun callValidateToken() {
        launch {
            setState { copy(isLoading = true) }

            when (val resource = validateTokenUseCase()) {
                is Resource.Success -> validateToken.value = resource.data
                is Resource.Error -> setError(resource.throwable)
            }

            setState { copy(isLoading = false) }
        }
    }

}
