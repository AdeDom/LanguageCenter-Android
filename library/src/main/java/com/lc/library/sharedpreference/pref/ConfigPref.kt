package com.lc.library.sharedpreference.pref

interface ConfigPref {

    var selectPage: String

    var isTranslateThToEn: Boolean

    var copyTextMessage: String?

    companion object {
        const val CONFIG_PREF = "config_pref"
        const val SELECT_PAGE_KEY = "select_page_key"
        const val SELECT_PAGE_COMMUNITY = "community"
        const val SELECT_PAGE_CHATS = "chats"
        const val SELECT_PAGE_FRIEND_GROUP = "friend_group"
        const val SELECT_PAGE_VOCABULARY = "vocabulary"
        const val SELECT_PAGE_ADD_VOCABULARY = "addVocabulary"
        const val SELECT_PAGE_VOCABULARY_DETAIL = "vocabularyDetail"
        const val SELECT_PAGE_PROFILE = "profile"
        const val SELECT_PAGE_USER_INFO = "user_info"
        const val SELECT_PAGE_TALK = "talk"
        const val SELECT_PAGE_FRIEND_GROUP_DETAIL = "friend_group_detail"
        const val SELECT_PAGE_EDIT_PROFILE = "edit_profile"
        const val SELECT_PAGE_EDIT_LOCALE_NATIVE = "edit_locale_native"
        const val SELECT_PAGE_EDIT_LOCALE_LEARNING = "edit_locale_learning"
        const val TRANSLATE_KEY = "translate_key"
        const val COPY_TEXT_MESSAGE_KEY = "copy_text_message_key"
    }

}
