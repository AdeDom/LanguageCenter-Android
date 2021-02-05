package com.lc.android.presentation.addvocabulary

data class AddVocabularyViewState(
    val vocabulary: String = "",
    val isValidateVocabulary: Boolean = false,
    val translation: String = "",
    val isValidateTranslation: Boolean = false,
    val source: String? = "",
    val target: String? = "",
    val isLoading: Boolean = false,
    val isClickable: Boolean = true,
)
