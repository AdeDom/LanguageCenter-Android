package com.lc.android.presentation.vocapbulary

import com.lc.server.models.model.VocabularyGroup

data class VocabularyViewState(
    val vocabularyGroups: List<VocabularyGroup> = emptyList(),
    val isLoading: Boolean = false,
)
