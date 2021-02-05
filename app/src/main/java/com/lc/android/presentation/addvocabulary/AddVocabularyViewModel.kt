package com.lc.android.presentation.addvocabulary

import com.lc.android.base.BaseViewModel
import com.lc.android.util.SingleLiveEvent
import com.lc.library.data.repository.Resource
import com.lc.library.presentation.usecase.AddVocabularyTranslationUseCase
import com.lc.server.models.request.AddVocabularyTranslationRequest
import com.lc.server.models.response.BaseResponse
import kotlinx.coroutines.launch

class AddVocabularyViewModel(
    private val addVocabularyTranslationUseCase: AddVocabularyTranslationUseCase,
) : BaseViewModel<AddVocabularyViewState>(AddVocabularyViewState()) {

    val addVocabularyTranslationEvent = SingleLiveEvent<BaseResponse>()

    fun callAddVocabularyTranslation(source: String, target: String) {
        launch {
            setState { copy(isLoading = true, isClickable = false) }

            if (state.value?.isValidateVocabulary == true && state.value?.isValidateTranslation == true) {
                val request = AddVocabularyTranslationRequest(
                    vocabulary = state.value?.vocabulary,
                    source = source,
                    target = target,
                    translations = listOf(state.value?.translation.orEmpty()),
                )
                when (val resource = addVocabularyTranslationUseCase(request)) {
                    is Resource.Success -> addVocabularyTranslationEvent.value = resource.data
                    is Resource.Error -> setError(resource.throwable)
                }
            }

            setState { copy(isLoading = false, isClickable = true) }
        }
    }

    fun setStateVocabulary(vocabulary: String) {
        setState {
            copy(
                vocabulary = vocabulary,
                isValidateVocabulary = vocabulary.isNotEmpty(),
            )
        }
    }

    fun setStateTranslation(translation: String) {
        setState {
            copy(
                translation = translation,
                isValidateTranslation = translation.isNotEmpty(),
            )
        }
    }

}
