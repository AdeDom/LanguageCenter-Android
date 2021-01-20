package com.lc.android.presentation.talk

sealed class MessagesType {

    object ChatLeft : MessagesType()
    object ChatRight : MessagesType()

}
