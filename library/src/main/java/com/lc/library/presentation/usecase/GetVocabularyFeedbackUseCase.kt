package com.lc.library.presentation.usecase

import com.lc.library.data.db.entities.VocabularyFeedbackEntity

interface GetVocabularyFeedbackUseCase {
    suspend operator fun invoke(): VocabularyFeedbackEntity?
}
