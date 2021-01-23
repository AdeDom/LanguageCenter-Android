package com.lc.library.data.model

data class ResendMessageRequest(
    val talkId: String? = null,
    val fromUserId: String? = null,
    val toUserId: String? = null,
    val messages: String? = null,
    val dateTimeLong: Long? = 0,
)
