package com.lc.android.presentation.talk

import com.lc.library.data.db.entities.TalkEntity
import com.lc.server.models.model.Vocabulary

data class TalkViewState(
    val resultTranslate: Vocabulary? = null,
    val isResultTranslate: Boolean = false,
    val resendMessageTalkEntity: TalkEntity? = null,
    val messages: String = "",
    val isSendMessage: Boolean = false,
    val isLoading: Boolean = false,
)
