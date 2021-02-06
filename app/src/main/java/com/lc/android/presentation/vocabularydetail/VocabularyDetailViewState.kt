package com.lc.android.presentation.vocabularydetail

import com.lc.server.models.model.Vocabulary

data class VocabularyDetailViewState(
    val vocabularies: List<Vocabulary> = emptyList(),
    val isLoading: Boolean = false,
)
