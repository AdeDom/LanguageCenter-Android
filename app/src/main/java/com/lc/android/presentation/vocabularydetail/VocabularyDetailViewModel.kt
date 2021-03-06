package com.lc.android.presentation.vocabularydetail

import com.lc.android.base.BaseViewModel
import com.lc.library.data.repository.Resource
import com.lc.library.presentation.usecase.FetchVocabularyDetailUseCase
import com.lc.library.presentation.usecase.FilePrefUseCase
import kotlinx.coroutines.launch

class VocabularyDetailViewModel(
    private val fetchVocabularyDetailUseCase: FetchVocabularyDetailUseCase,
    private val filePrefUseCase: FilePrefUseCase,
) : BaseViewModel<VocabularyDetailViewState>(VocabularyDetailViewState()) {

    fun callFetchVocabularyDetailUseCase(vocabularyGroupId: Int) {
        launch {
            setState { copy(isLoading = true) }

            when (val resource = fetchVocabularyDetailUseCase(vocabularyGroupId)) {
                is Resource.Success -> setState { copy(vocabularies = resource.data.vocabularies) }
                is Resource.Error -> setError(resource.throwable)
            }

            setState { copy(isLoading = false) }
        }
    }

    fun setCopyTextMessage(copyTextMessage: String?) {
        filePrefUseCase.setCopyTextMessage(copyTextMessage)
    }

}
