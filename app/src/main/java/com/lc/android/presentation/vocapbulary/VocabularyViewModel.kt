package com.lc.android.presentation.vocapbulary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lc.android.base.BaseViewModel
import com.lc.android.presentation.model.VocabularyFeedbackParcelable
import com.lc.android.util.SingleLiveEvent
import com.lc.library.data.db.entities.VocabularyFeedbackEntity
import com.lc.library.data.repository.Resource
import com.lc.library.presentation.usecase.FetchVocabularyGroupUseCase
import com.lc.library.presentation.usecase.GetVocabularyFeedbackUseCase
import com.lc.library.presentation.usecase.VocabularyTranslationFeedbackUseCase
import com.lc.server.models.request.TranslationFeedbackRequest
import com.lc.server.models.request.VocabularyTranslationFeedbackRequest
import com.lc.server.models.response.BaseResponse
import kotlinx.coroutines.launch

class VocabularyViewModel(
    private val fetchVocabularyGroupUseCase: FetchVocabularyGroupUseCase,
    private val getVocabularyFeedbackUseCase: GetVocabularyFeedbackUseCase,
    private val vocabularyTranslationFeedbackUseCase: VocabularyTranslationFeedbackUseCase,
) : BaseViewModel<VocabularyViewState>(VocabularyViewState()) {

    val getVocabularyFeedback = SingleLiveEvent<VocabularyFeedbackEntity>()

    private val _vocabularyTranslationFeedbackEvent = MutableLiveData<BaseResponse>()
    val vocabularyTranslationFeedbackEvent: LiveData<BaseResponse>
        get() = _vocabularyTranslationFeedbackEvent

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

    fun callVocabularyTranslationFeedback(vocabularyFeedback: VocabularyFeedbackParcelable?) {
        launch {
            setState { copy(isLoading = true) }

            val request = VocabularyTranslationFeedbackRequest(
                vocabularyId = vocabularyFeedback?.vocabularyId,
                rating = vocabularyFeedback?.rating,
                translations = vocabularyFeedback?.translations?.map {
                    TranslationFeedbackRequest(
                        translationId = it.translationId,
                        isCorrect = it.isCorrect,
                    )
                } ?: emptyList(),
            )
            when (val resource = vocabularyTranslationFeedbackUseCase(request)) {
                is Resource.Success -> _vocabularyTranslationFeedbackEvent.value = resource.data
                is Resource.Error -> setError(resource.throwable)
            }

            setState { copy(isLoading = false) }
        }
    }

}
