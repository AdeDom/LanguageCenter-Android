package com.lc.library.domain.usecase

import com.lc.library.data.db.entities.VocabularyFeedbackEntity
import com.lc.library.data.network.source.LanguageCenterDataSource
import com.lc.library.presentation.usecase.GetVocabularyFeedbackUseCase

class GetVocabularyFeedbackUseCaseImpl(
    private val dataSource: LanguageCenterDataSource,
) : GetVocabularyFeedbackUseCase {
    override suspend fun invoke(): VocabularyFeedbackEntity? {
        return dataSource.getDbVocabularyFeedback()
    }
}
