package com.lc.library.presentation.usecase

import com.lc.library.data.repository.Resource
import com.lc.server.models.response.LanguageCenterTranslateResponse

interface LanguageCenterTranslateUseCase {
    suspend operator fun invoke(vocabulary: String?): Resource<LanguageCenterTranslateResponse>
}
