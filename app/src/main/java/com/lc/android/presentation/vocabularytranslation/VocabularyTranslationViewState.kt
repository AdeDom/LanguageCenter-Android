package com.lc.android.presentation.vocabularytranslation

import com.lc.server.models.model.Vocabulary

data class VocabularyTranslationViewState(
    var vocabularies: List<Vocabulary> = emptyList(),
    val isLoading: Boolean = false,
)
