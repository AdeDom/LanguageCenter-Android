package com.lc.library.domain.usecase

import com.lc.library.presentation.usecase.FilePrefUseCase
import com.lc.library.sharedpreference.pref.AuthPref
import com.lc.library.sharedpreference.pref.ConfigPref

class FilePrefUseCaseImpl(
    private val authPref: AuthPref,
    private val configPref: ConfigPref,
) : FilePrefUseCase {

    override fun getAccessToken(): String {
        return authPref.accessToken
    }

    override fun getRefreshToken(): String {
        return authPref.refreshToken
    }

    override fun getSelectPage(): String {
        return configPref.selectPage
    }

    override fun setSelectPage(selectPage: String) {
        configPref.selectPage = selectPage
    }

    override fun getIsTranslateThToEn(): Boolean {
        return configPref.isTranslateThToEn
    }

    override fun setIsTranslateThToEn(isTranslateThToEn: Boolean) {
        configPref.isTranslateThToEn = isTranslateThToEn
    }

    override fun getCopyTextMessage(): String? {
        return configPref.copyTextMessage
    }

    override fun setCopyTextMessage(copyTextMessage: String?) {
        configPref.copyTextMessage = copyTextMessage
    }

}
