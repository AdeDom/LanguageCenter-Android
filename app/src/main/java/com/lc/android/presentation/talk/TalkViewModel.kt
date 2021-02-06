package com.lc.android.presentation.talk

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lc.android.base.BaseViewModel
import com.lc.android.presentation.model.UserInfoParcelable
import com.lc.android.util.SingleLiveEvent
import com.lc.library.data.db.entities.ChatListEntity
import com.lc.library.data.db.entities.TalkEntity
import com.lc.library.data.repository.Resource
import com.lc.library.presentation.usecase.*
import com.lc.server.models.model.UserInfoLocale
import com.lc.server.models.model.Vocabulary
import com.lc.server.models.request.ResendMessageRequest
import com.lc.server.models.request.SendMessageRequest
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TalkViewModel(
    private val sendMessageUseCase: SendMessageUseCase,
    private val getTalkUseCase: GetTalkUseCase,
    private val readMessagesUseCase: ReadMessagesUseCase,
    private val saveChatListUseCase: SaveChatListUseCase,
    private val resendMessageUseCase: ResendMessageUseCase,
    private val filePrefUseCase: FilePrefUseCase,
    private val languageCenterTranslateUseCase: LanguageCenterTranslateUseCase,
) : BaseViewModel<TalkViewState>(TalkViewState()) {

    private val _talkWebSockets = MutableLiveData<Unit>().apply { value = Unit }
    val talkWebSockets: LiveData<Unit>
        get() = _talkWebSockets

    private val _clearTextEvent = MutableLiveData<Unit>()
    val clearTextEvent: LiveData<Unit>
        get() = _clearTextEvent

    val translateResultsEvent = SingleLiveEvent<Vocabulary>()

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

            val talkId = sendMessageUseCase.getTalkId()

            val request = SendMessageRequest(
                talkId = talkId,
                messages = message,
                toUserId = toUserId,
            )
            when (val resource = sendMessageUseCase(request)) {
                is Resource.Error -> setError(resource.throwable)
                null -> handleCallWebSocketsIsNull(talkId)
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
        if (userInfo.userId.isNullOrBlank()) return

        launch {
            val entity = ChatListEntity(
                userId = userInfo.userId,
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
        val entity = state.value?.resendMessageTalkEntity
        if (entity?.talkId == null) return

        launch {
            val request = ResendMessageRequest(
                talkId = entity.talkId,
                fromUserId = entity.fromUserId,
                toUserId = entity.toUserId,
                messages = entity.messages,
                dateTimeLong = entity.dateTimeLong,
            )
            when (val resource = resendMessageUseCase(request)) {
                is Resource.Error -> setError(resource.throwable)
                null -> handleCallWebSocketsIsNull(entity.talkId)
            }
        }
    }

    private suspend fun handleCallWebSocketsIsNull(talkId: String) {
        _talkWebSockets.value = Unit

        delay(1_000)

        val talkEntity = resendMessageUseCase.getDbTalkByTalkId(talkId) ?: return
        val request = ResendMessageRequest(
            talkId = talkEntity.talkId,
            fromUserId = talkEntity.fromUserId,
            toUserId = talkEntity.toUserId,
            messages = talkEntity.messages,
            dateTimeLong = talkEntity.dateTimeLong,
        )
        resendMessageUseCase(request)
    }

    fun callLanguageCenterTranslate(translateText: String) {
        launch {
            setState { copy(isLoading = true) }

            when (val resource = languageCenterTranslateUseCase(translateText)) {
                is Resource.Success -> {
                    setState {
                        copy(
                            isResultTranslate = resource.data.vocabulary != null,
                            resultTranslate = resource.data.vocabulary
                        )
                    }
                    translateResultsEvent.value = resource.data.vocabulary
                }
                is Resource.Error -> setError(resource.throwable)
            }

            setState { copy(isLoading = false) }
        }
    }

    fun setStateIsResultTranslateHide() {
        setState { copy(isResultTranslate = false) }
    }

    fun getIsTranslateThToEn(): Boolean {
        return filePrefUseCase.getIsTranslateThToEn()
    }

    fun setIsTranslateThToEn(isTranslateThToEn: Boolean) {
        filePrefUseCase.setIsTranslateThToEn(isTranslateThToEn)
    }

    fun callSendMessageTranslate(toUserId: String?) {
        launch {
            setState { copy(isResultTranslate = false) }

            val talkId = sendMessageUseCase.getTalkId()

            val request = SendMessageRequest(
                talkId = talkId,
                messages = state.value?.resultTranslate?.translations?.singleOrNull()?.translation,
                toUserId = toUserId,
            )
            when (val resource = sendMessageUseCase(request)) {
                is Resource.Error -> setError(resource.throwable)
                null -> handleCallWebSocketsIsNull(talkId)
            }
        }
    }

    fun getCopyTextMessage(): String? {
        return filePrefUseCase.getCopyTextMessage()
    }

}
