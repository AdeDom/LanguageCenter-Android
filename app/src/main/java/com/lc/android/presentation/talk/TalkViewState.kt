package com.lc.android.presentation.talk

import com.lc.library.data.db.entities.TalkEntity

data class TalkViewState(
    val resultTranslate: Pair<String, List<String>>? = null,
    val isResultTranslate: Boolean = false,
    val resendMessageTalkEntity: TalkEntity? = null,
    val messages: String = "",
    val isSendMessage: Boolean = false,
    val isLoading: Boolean = false,
)
