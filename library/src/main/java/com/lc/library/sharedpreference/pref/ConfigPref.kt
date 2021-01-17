package com.lc.library.sharedpreference.pref

interface ConfigPref {

    var selectPage: String

    companion object {
        const val CONFIG_PREF = "config_pref"
        const val SELECT_PAGE_KEY = "select_page_key"
        const val SELECT_PAGE_COMMUNITY = "community"
        const val SELECT_PAGE_CHATS = "chats"
        const val SELECT_PAGE_CHAT_GROUP = "chat_group"
        const val SELECT_PAGE_VOCABULARY = "vocabulary"
        const val SELECT_PAGE_PROFILE = "profile"
    }

}
