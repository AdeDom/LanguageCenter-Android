package com.lc.android.presentation.talk

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lc.android.base.BaseViewModel
import com.lc.android.presentation.model.UserInfoParcelable
import com.lc.library.data.db.entities.ChatListEntity
import com.lc.library.data.db.entities.TalkEntity
import com.lc.library.data.model.ResendMessageRequest
import com.lc.library.data.repository.Resource
import com.lc.library.presentation.usecase.*
import com.lc.server.models.model.UserInfoLocale
import com.lc.server.models.request.SendMessageRequest
import kotlinx.coroutines.launch

class TalkViewModel(
    private val sendMessageUseCase: SendMessageUseCase,
    private val getTalkUseCase: GetTalkUseCase,
    private val readMessagesUseCase: ReadMessagesUseCase,
    private val saveChatListUseCase: SaveChatListUseCase,
    private val resendMessageUseCase: ResendMessageUseCase,
) : BaseViewModel<TalkViewState>(TalkViewState()) {

    private val _clearTextEvent = MutableLiveData<Unit>()
    val clearTextEvent: LiveData<Unit>
        get() = _clearTextEvent

    fun getDbTalkByOtherUserIdLiveData(otherUserId: String?): LiveData<List<TalkEntity>> {
        return getTalkUseCase.getDbTalkByOtherUserIdLiveData(otherUserId)
    }

    fun setStateMessages(messages: String) {
        setState {
            copy(
                messages = messages,
                isSendMessage = messages.isNotBlank(),
            )
        }
    }

    fun callSendMessage(toUserId: String?) {
        launch {
            val message = state.value?.messages
            _clearTextEvent.value = Unit

            val request = SendMessageRequest(
                messages = message,
                toUserId = toUserId,
            )
            when (val resource = sendMessageUseCase(request)) {
                is Resource.Error -> setError(resource.throwable)
            }
        }
    }

    fun callReadMessages(readUserId: String?) {
        launch {
            when (val resource = readMessagesUseCase(readUserId)) {
                is Resource.Error -> setError(resource.throwable)
            }
        }
    }

    fun saveChatList(userInfo: UserInfoParcelable) {
        launch {
            val entity = ChatListEntity(
                userId = userInfo.userId.orEmpty(),
                email = userInfo.email,
                givenName = userInfo.givenName,
                familyName = userInfo.familyName,
                name = userInfo.name,
                picture = userInfo.picture,
                gender = userInfo.gender,
                age = userInfo.age,
                birthDateString = userInfo.birthDateString,
                birthDateLong = userInfo.birthDateLong,
                aboutMe = userInfo.aboutMe,
                localNatives = userInfo.localNatives?.map {
                    UserInfoLocale(locale = it.locale, level = it.level)
                } ?: emptyList(),
                localLearnings = userInfo.localLearnings?.map {
                    UserInfoLocale(locale = it.locale, level = it.level)
                } ?: emptyList(),
                messages = "",
                dateTimeString = "",
                dateTimeLong = 0,
            )
            saveChatListUseCase(entity)
        }
    }

    fun setStateResendMessageTalkEntity(resendMessageTalkEntity: TalkEntity) {
        setState { copy(resendMessageTalkEntity = resendMessageTalkEntity) }
    }

    fun callResendMessage() {
        launch {
            val entity = state.value?.resendMessageTalkEntity
            val request = ResendMessageRequest(
                talkId = entity?.talkId,
                fromUserId = entity?.fromUserId,
                toUserId = entity?.toUserId,
                messages = entity?.messages,
                dateTimeLong = entity?.dateTimeLong,
            )
            when (val resource = resendMessageUseCase(request)) {
                is Resource.Error -> setError(resource.throwable)
            }
        }
    }

}
