package com.lc.library.presentation.usecase

interface FilePrefUseCase {

    fun getAccessToken(): String

    fun getRefreshToken(): String

    fun getSelectPage(): String

    fun setSelectPage(selectPage: String)

    fun getIsTranslateThToEn(): Boolean

    fun setIsTranslateThToEn(isTranslateThToEn: Boolean)

}
