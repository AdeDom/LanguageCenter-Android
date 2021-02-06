package com.lc.android.presentation.vocapbulary

import com.lc.android.base.BaseViewModel
import com.lc.library.data.repository.Resource
import com.lc.library.presentation.usecase.FetchVocabularyGroupUseCase
import kotlinx.coroutines.launch

class VocabularyViewModel(
    private val fetchVocabularyGroupUseCase: FetchVocabularyGroupUseCase,
) : BaseViewModel<VocabularyViewState>(VocabularyViewState()) {

    fun callFetchVocabularyGroupUseCase() {
        launch {
            setState { copy(isLoading = true) }

            when (val resource = fetchVocabularyGroupUseCase()) {
                is Resource.Success -> setState { copy(vocabularyGroups = resource.data.vocabularyGroups) }
                is Resource.Error -> setError(resource.throwable)
            }

            setState { copy(isLoading = false) }
        }
    }

}
