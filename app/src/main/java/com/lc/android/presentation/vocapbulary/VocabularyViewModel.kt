package com.lc.android.presentation.vocapbulary

import com.lc.android.base.BaseViewModel
import com.lc.android.util.SingleLiveEvent
import com.lc.library.data.db.entities.VocabularyFeedbackEntity
import com.lc.library.data.repository.Resource
import com.lc.library.presentation.usecase.FetchVocabularyGroupUseCase
import com.lc.library.presentation.usecase.GetVocabularyFeedbackUseCase
import kotlinx.coroutines.launch

class VocabularyViewModel(
    private val fetchVocabularyGroupUseCase: FetchVocabularyGroupUseCase,
    private val getVocabularyFeedbackUseCase: GetVocabularyFeedbackUseCase,
) : BaseViewModel<VocabularyViewState>(VocabularyViewState()) {

    val getVocabularyFeedback = SingleLiveEvent<VocabularyFeedbackEntity>()

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

    fun randomVocabularyFeedback() {
        if ((1..5).random() == 3) {
            launch {
                getVocabularyFeedbackUseCase()?.let {
                    getVocabularyFeedback.value = it
                }
            }
        }
    }

}
