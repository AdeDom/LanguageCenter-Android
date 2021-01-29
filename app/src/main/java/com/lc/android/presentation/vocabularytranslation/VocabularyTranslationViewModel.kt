package com.lc.android.presentation.vocabularytranslation

import com.lc.android.base.BaseViewModel
import com.lc.library.data.repository.Resource
import com.lc.library.presentation.usecase.FetchVocabularyTranslationUseCase
import kotlinx.coroutines.launch

class VocabularyTranslationViewModel(
    private val fetchVocabularyTranslationUseCase: FetchVocabularyTranslationUseCase,
) : BaseViewModel<VocabularyTranslationViewState>(VocabularyTranslationViewState()) {

    fun callFetchVocabularyTranslation() {
        launch {
            setState { copy(isLoading = true) }

            when (val resource = fetchVocabularyTranslationUseCase()) {
                is Resource.Success -> setState { copy(vocabularies = resource.data.vocabularies) }
                is Resource.Error -> setError(resource.throwable)
            }

            setState { copy(isLoading = false) }
        }
    }

}
