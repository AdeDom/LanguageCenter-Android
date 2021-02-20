package com.lc.library.data.repository

import com.lc.library.data.db.entities.*
import com.lc.library.data.network.source.LanguageCenterDataSource
import com.lc.library.domain.repository.LanguageCenterRepository
import com.lc.library.sharedpreference.pref.AuthPref
import com.lc.library.sharedpreference.pref.ConfigPref
import com.lc.library.util.LanguageCenterUtils
import com.lc.server.models.model.AddChatGroupDetail
import com.lc.server.models.model.TalkSendMessageWebSocket
import com.lc.server.models.model.Translation
import com.lc.server.models.model.Vocabulary
import com.lc.server.models.request.*
import com.lc.server.models.response.*
import com.lc.server.util.LanguageCenterConstant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class LanguageCenterRepositoryImpl(
    private val dataSource: LanguageCenterDataSource,
    private val authPref: AuthPref,
    private val configPref: ConfigPref,
) : LanguageCenterRepository {

    private suspend fun <T : Any> safeApiCall(apiCall: suspend () -> T): Resource<T> =
        withContext(Dispatchers.IO) {
            try {
                Resource.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                if (throwable is HttpException && (throwable.code() == 401 || throwable.code() == 403)) {
                    Resource.Error(throwable, true)
                } else {
                    Resource.Error(throwable, false)
                }
            }
        }

    override suspend fun callSignIn(request: SignInRequest): Resource<SignInResponse> {
        val response = safeApiCall { dataSource.callSignIn(request) }

        if (response is Resource.Success) {
            saveTokenAuth(response.data)
            callFetchUserInfo()
            callFetchFriendInfo()
        }

        return response
    }

    override suspend fun callValidateToken(): Resource<BaseResponse> {
        val resource = safeApiCall { dataSource.callValidateToken(authPref.refreshToken) }

        if (resource is Resource.Success && !resource.data.success) {
            signOut()
        }

        return resource
    }

    private fun saveTokenAuth(response: SignInResponse) {
        if (response.success) {
            authPref.accessToken = response.token?.accessToken.orEmpty()
            authPref.refreshToken = response.token?.refreshToken.orEmpty()
        }
    }

    private suspend fun callFetchUserInfo(): Resource<UserInfoResponse> {
        val response = safeApiCall { dataSource.callFetchUserInfo() }

        if (response is Resource.Success) {
            val data = response.data
            if (data.success) {
                val userInfo = data.userInfo
                val entity = UserInfoEntity(
                    userId = userInfo?.userId.orEmpty(),
                    email = userInfo?.email,
                    givenName = userInfo?.givenName,
                    familyName = userInfo?.familyName,
                    name = userInfo?.name,
                    picture = userInfo?.picture,
                    gender = userInfo?.gender,
                    birthDateString = userInfo?.birthDateString,
                    birthDateLong = userInfo?.birthDateLong,
                    verifiedEmail = userInfo?.verifiedEmail ?: false,
                    aboutMe = userInfo?.aboutMe,
                    created = userInfo?.created,
                    updated = userInfo?.updated,
                    localNatives = userInfo?.localNatives ?: emptyList(),
                    localLearnings = userInfo?.localLearnings ?: emptyList(),
                )
                dataSource.saveUserInfo(entity)
            }
        }

        return response
    }

    override suspend fun signOut() {
        authPref.accessToken = ""
        authPref.refreshToken = ""
        dataSource.deleteUserInfo()
        dataSource.deleteFriendInfo()
        dataSource.deleteAllAddChatGroupDetail()
        dataSource.deleteTalk()
        dataSource.deleteChatList()
    }

    override suspend fun saveChatListEntity(chatListEntity: ChatListEntity) {
        val count = dataSource.getDbChatListCountByUserId(chatListEntity.userId)
        if (count == 0) dataSource.saveChatListEntity(chatListEntity)
    }

    override suspend fun callGuideUpdateProfile(guideUpdateProfileRequest: GuideUpdateProfileRequest): Resource<BaseResponse> {
        val response = safeApiCall { dataSource.callGuideUpdateProfile(guideUpdateProfileRequest) }

        if (response is Resource.Success) callFetchUserInfo()

        return response
    }

    override suspend fun callEditProfile(editProfileRequest: EditProfileRequest): Resource<BaseResponse> {
        val response = safeApiCall { dataSource.callEditProfile(editProfileRequest) }

        if (response is Resource.Success) callFetchUserInfo()

        return response
    }

    override suspend fun callEditLocale(editLocaleRequest: EditLocaleRequest): Resource<BaseResponse> {
        val response = safeApiCall { dataSource.callEditLocale(editLocaleRequest) }

        if (response is Resource.Success) callFetchUserInfo()

        return response
    }

    private suspend fun callFetchFriendInfo(): Resource<FetchFriendInfoResponse> {
        val resource = safeApiCall { dataSource.callFetchFriendInfo() }

        if (resource is Resource.Success) {
            if (resource.data.success) {
                val friendInfoList = resource.data.friendInfoList
                friendInfoList.forEach { friendInfo ->
                    val entity = FriendInfoEntity(
                        userId = friendInfo.userId.orEmpty(),
                        email = friendInfo.email,
                        givenName = friendInfo.givenName,
                        familyName = friendInfo.familyName,
                        name = friendInfo.name,
                        picture = friendInfo.picture,
                        gender = friendInfo.gender,
                        age = friendInfo.age,
                        birthDateString = friendInfo.birthDateString,
                        birthDateLong = friendInfo.birthDateLong,
                        aboutMe = friendInfo.aboutMe,
                        localNatives = friendInfo.localNatives,
                        localLearnings = friendInfo.localLearnings,
                    )
                    dataSource.saveFriendInfo(entity)
                }
            }
        }

        return resource
    }

    override suspend fun callFetchCommunity(): Resource<FetchCommunityResponse> {
        return safeApiCall { dataSource.callFetchCommunity() }
    }

    override suspend fun callAddAlgorithm(addAlgorithmRequest: AddAlgorithmRequest): Resource<BaseResponse> {
        return safeApiCall { dataSource.callAddAlgorithm(addAlgorithmRequest) }
    }

    override suspend fun callSendMessage(sendMessageRequest: SendMessageRequest): Resource<SendMessageResponse>? {
        val fromUserId = dataSource.getDbUserInfo()?.userId
        val dateTimeLong = LanguageCenterUtils.getCurrentTimeMillis()
        val entity = TalkEntity(
            talkId = sendMessageRequest.talkId.orEmpty(),
            fromUserId = fromUserId.orEmpty(),
            toUserId = sendMessageRequest.toUserId.orEmpty(),
            messages = sendMessageRequest.messages.orEmpty(),
            dateString = LanguageCenterUtils.getDateFormat(dateTimeLong),
            timeString = LanguageCenterUtils.getTimeFormat(dateTimeLong),
            dateTimeLong = dateTimeLong,
            isRead = false,
            isSendMessage = false,
            isSendType = true,
        )
        dataSource.saveTalk(entity)

        val request = sendMessageRequest.copy(talkId = sendMessageRequest.talkId)
        val resource = safeApiCall { dataSource.callSendMessage(request) }

        if (resource is Resource.Success) {
            if (resource.data.success) {
                webSocketsSendMessage(
                    sendMessageRequest.talkId,
                    fromUserId,
                    sendMessageRequest.toUserId,
                    sendMessageRequest.messages,
                    resource.data.dateTimeLong
                ) ?: return null
            }
        }

        return resource
    }

    override suspend fun callReadMessages(readUserId: String?): Resource<BaseResponse> {
        return safeApiCall { dataSource.callReadMessages(readUserId) }
    }

    override suspend fun callResendMessage(resendMessageRequest: ResendMessageRequest): Resource<BaseResponse>? {
        val resource = safeApiCall { dataSource.callResendMessage(resendMessageRequest) }

        if (resource is Resource.Success) {
            if (resource.data.success) {
                val (talkId, fromUserId, toUserId, messages, dateTimeLong) = resendMessageRequest
                webSocketsSendMessage(
                    talkId,
                    fromUserId,
                    toUserId,
                    messages,
                    dateTimeLong
                ) ?: return null
            }
        }

        return resource
    }

    private suspend fun webSocketsSendMessage(
        talkId: String?,
        fromUserId: String?,
        toUserId: String?,
        messages: String?,
        dateTimeLong: Long?,
    ): Unit? {
        // update send message
        dataSource.updateTalkSendMessage(
            talkId = talkId.orEmpty(),
            dateString = LanguageCenterUtils.getDateFormat(dateTimeLong ?: 0),
            timeString = LanguageCenterUtils.getTimeFormat(dateTimeLong ?: 0),
            dateTimeLong = dateTimeLong ?: 0,
            isSendMessage = true,
        )

        // update chat list
        dataSource.updateChatListNewMessage(
            userId = toUserId.orEmpty(),
            messages = messages.orEmpty(),
            dateTimeString = LanguageCenterUtils.getDateTimeFormat(dateTimeLong ?: 0),
            dateTimeLong = dateTimeLong ?: 0,
        )

        // call web socket
        val talkSendMessageWebSocket = TalkSendMessageWebSocket(
            talkId = talkId.orEmpty(),
            fromUserId = fromUserId.orEmpty(),
            toUserId = toUserId.orEmpty(),
            messages = messages.orEmpty(),
            dateTimeLong = dateTimeLong ?: 0,
        )
        repeat(3) {
            dataSource.outgoingSendMessageSocket(talkSendMessageWebSocket) ?: return null
            delay(500)
        }

        return Unit
    }

    override suspend fun callFetchTalkUnreceived(): Resource<FetchTalkUnreceivedResponse> {
        val resource = safeApiCall { dataSource.callFetchTalkUnreceived() }

        if (resource is Resource.Success) {
            if (resource.data.success) {
                resource.data.talks.forEach { talk ->
                    // save talk
                    val entity = TalkEntity(
                        talkId = talk.talkId,
                        fromUserId = talk.fromUserId,
                        toUserId = talk.toUserId,
                        messages = talk.messages,
                        dateString = LanguageCenterUtils.getDateFormat(talk.dateTime),
                        timeString = LanguageCenterUtils.getTimeFormat(talk.dateTime),
                        dateTimeLong = talk.dateTime,
                        isRead = talk.isRead,
                        isSendMessage = talk.isSendMessage,
                        isSendType = false,
                    )
                    dataSource.saveTalk(entity)

                    // save chat list
                    saveChatListDatabase(
                        fromUserId = talk.fromUserId,
                        toUserId = talk.toUserId,
                        messages = talk.messages,
                        dateTimeLong = talk.dateTime,
                    )
                }

                // call api put talk is receive message
                if (resource.data.talks.isNotEmpty()) {
                    val talkIdList = resource.data.talks.map { it.talkId }
                    val request = UpdateReceiveMessageRequest(talkIdList)
                    safeApiCall { dataSource.callUpdateReceiveMessages(request) }
                }
            }
        }

        return resource
    }

    override suspend fun callLanguageCenterTranslate(vocabulary: String?): Resource<LanguageCenterTranslateResponse> {
        var resource = safeApiCall { dataSource.callLanguageCenterTranslate(vocabulary) }

        if (resource is Resource.Success && resource.data.vocabulary == null && vocabulary != null) {
            // get source & target
            var source = ""
            var target = ""
            when (configPref.isTranslateThToEn) {
                true -> {
                    source = LanguageCenterConstant.THAI
                    target = LanguageCenterConstant.ENGLISH
                }
                false -> {
                    source = LanguageCenterConstant.ENGLISH
                    target = LanguageCenterConstant.THAI
                }
            }

            // call api google translate
            val googleTranslate = safeApiCall {
                dataSource.callGoogleTranslate(
                    vocabulary = vocabulary,
                    source = source,
                    target = target,
                )
            }

            if (googleTranslate is Resource.Success) {
                googleTranslate.data
                    .data
                    ?.translations
                    ?.map { it.translatedText.orEmpty() }
                    ?.filter { it.isNotBlank() }
                    ?.let { translations ->
                        // save translation to server
                        val request = AddVocabularyTranslationRequest(
                            vocabulary = vocabulary,
                            source = source,
                            target = target,
                            translations = translations,
                            reference = LanguageCenterConstant.GOOGLE_TRANSLATE,
                        )
                        callAddVocabularyTranslation(request)

                        // response resource to view UI
                        val languageCenterTranslateResponse = LanguageCenterTranslateResponse(
                            success = true,
                            message = "",
                            vocabulary = Vocabulary(
                                vocabularyId = "",
                                vocabulary = vocabulary,
                                sourceLanguage = source,
                                created = 0,
                                vocabularyGroupName = "",
                                translations = translations.map {
                                    Translation(
                                        translationId = 0,
                                        translation = it,
                                        targetLanguage = target,
                                    )
                                },
                            ),
                        )
                        resource = Resource.Success(languageCenterTranslateResponse)
                    }
            }
        }

        // save vocabulary feedback
        if (resource is Resource.Success) {
            val success = (resource as Resource.Success<LanguageCenterTranslateResponse>)
            val vocabularyId = success.data.vocabulary?.vocabularyId

            val countVocabulary = vocabularyId?.let { dataSource.getDbVocabularyIsEvaluation(it) }
            if (countVocabulary == 0) {
                success.data.vocabulary?.let {
                    val entity = VocabularyFeedbackEntity(
                        vocabularyId = it.vocabularyId,
                        userId = it.userInfo?.userId,
                        vocabulary = it.vocabulary,
                        sourceLanguage = it.sourceLanguage,
                        reference = it.reference,
                        vocabularyGroupName = it.vocabularyGroupName,
                        translations = it.translations,
                        isEvaluation = false,
                        created = System.currentTimeMillis(),
                    )
                    dataSource.saveVocabularyFeedback(entity)
                }
            }
        }

        return resource
    }

    override suspend fun callAddChatGroup(addChatGroupRequest: AddChatGroupRequest): Resource<BaseResponse> {
        return safeApiCall { dataSource.callAddChatGroup(addChatGroupRequest) }
    }

    override suspend fun callFetchChatGroup(): Resource<FetchChatGroupResponse> {
        return safeApiCall { dataSource.callFetchChatGroup() }
    }

    override suspend fun callFetchChatGroupDetail(chatGroupId: Int?): Resource<FetchChatGroupDetailResponse> {
        return safeApiCall { dataSource.callFetchChatGroupDetail(chatGroupId) }
    }

    override suspend fun callRenameChatGroup(renameChatGroupRequest: RenameChatGroupRequest): Resource<BaseResponse> {
        return safeApiCall { dataSource.callRenameChatGroup(renameChatGroupRequest) }
    }

    override suspend fun callRemoveChatGroup(chatGroupId: Int?): Resource<BaseResponse> {
        return safeApiCall { dataSource.callRemoveChatGroup(chatGroupId) }
    }

    override suspend fun callFetchAddChatGroupDetail(): Resource<FetchAddChatGroupDetailResponse> {
        val resource = safeApiCall { dataSource.callFetchAddChatGroupDetail() }

        if (resource is Resource.Success) {
            var addChatGroupDetailList = resource.data.addChatGroupDetailList

            // filter
            val otherFriendUserId = dataSource.getDbAddChatGroupDetailList()?.map { it.userId }
            val list = mutableListOf<AddChatGroupDetail>()
            otherFriendUserId?.forEach { userId ->
                val element = addChatGroupDetailList.find { it.userId == userId }
                if (element != null) list.add(element)
            }
            addChatGroupDetailList = addChatGroupDetailList - list

            addChatGroupDetailList.forEach {
                val entity = AddChatGroupDetailEntity(
                    userId = it.userId.orEmpty(),
                    email = it.email,
                    givenName = it.givenName,
                    familyName = it.familyName,
                    name = it.name,
                    picture = it.picture,
                    gender = it.gender,
                    age = it.age,
                    birthDateString = it.birthDateString,
                    birthDateLong = it.birthDateLong,
                    aboutMe = it.aboutMe,
                    created = it.created,
                    localNatives = it.localNatives,
                    localLearnings = it.localLearnings,
                )
                dataSource.saveAddChatGroupDetail(entity)
            }
        }

        return resource
    }

    override suspend fun callChangeChatGroup(changeChatGroupRequest: ChangeChatGroupRequest): Resource<BaseResponse> {
        return safeApiCall { dataSource.callChangeChatGroup(changeChatGroupRequest) }
    }

    override suspend fun callRemoveChatGroupDetail(removeChatGroupDetailRequest: RemoveChatGroupDetailRequest): Resource<BaseResponse> {
        return safeApiCall { dataSource.callRemoveChatGroupDetail(removeChatGroupDetailRequest) }
    }

    override suspend fun callAddChatGroupFriend(
        addChatGroupFriendRequest: AddChatGroupFriendRequest,
        friendInfoEntity: FriendInfoEntity,
    ): Resource<BaseResponse> {
        val resource = safeApiCall { dataSource.callAddChatGroupFriend(addChatGroupFriendRequest) }

        if (resource is Resource.Success) {
            if (resource.data.success) {
                dataSource.saveFriendInfo(friendInfoEntity)

                dataSource.deleteAddChatGroupDetail(addChatGroupFriendRequest.friendUserId)
            }
        }

        return resource
    }

    override suspend fun callAddVocabularyTranslation(addVocabularyTranslationRequest: AddVocabularyTranslationRequest): Resource<BaseResponse> {
        return safeApiCall { dataSource.callAddVocabularyTranslation(addVocabularyTranslationRequest) }
    }

    override suspend fun callFetchVocabularyGroup(): Resource<FetchVocabularyGroupResponse> {
        return safeApiCall { dataSource.callFetchVocabularyGroup() }
    }

    override suspend fun callFetchVocabularyDetail(vocabularyGroupId: Int): Resource<FetchVocabularyDetailResponse> {
        return safeApiCall { dataSource.callFetchVocabularyDetail(vocabularyGroupId) }
    }

    override suspend fun incomingSendMessageSocket() {
        dataSource.incomingSendMessageSocket {
            val count = dataSource.getDbCountTalkByTalkId(it.talkId)
            if (count == 0) {
                // talk chat message
                val receiveMessage = safeApiCall { dataSource.callReceiveMessage(it.talkId) }
                if (receiveMessage is Resource.Success) {
                    if (receiveMessage.data.success) {
                        sendMessageCompleted(it)
                    }
                }

                // chat list
                saveChatListDatabase(
                    fromUserId = it.fromUserId,
                    toUserId = it.toUserId,
                    messages = it.messages,
                    dateTimeLong = it.dateTimeLong,
                )
            }
        }
    }

    private suspend fun sendMessageCompleted(socket: TalkSendMessageWebSocket) {
        val entity = TalkEntity(
            talkId = socket.talkId,
            fromUserId = socket.fromUserId,
            toUserId = socket.toUserId,
            messages = socket.messages,
            dateString = LanguageCenterUtils.getDateFormat(socket.dateTimeLong),
            timeString = LanguageCenterUtils.getTimeFormat(socket.dateTimeLong),
            dateTimeLong = socket.dateTimeLong,
            isRead = false,
            isSendMessage = true,
            isSendType = false,
        )
        dataSource.saveTalk(entity)
    }

    private suspend fun saveChatListDatabase(
        fromUserId: String,
        toUserId: String,
        messages: String,
        dateTimeLong: Long,
    ) {
        val getDbUserInfoUserId = dataSource.getDbUserInfo()?.userId

        val listUserId = mutableListOf<String>()
        listUserId.add(fromUserId)
        listUserId.add(toUserId)
        val userId = listUserId.singleOrNull { it != getDbUserInfoUserId }

        val count = userId?.let { dataSource.getDbChatListCountByUserId(it) }
        if (count != null && count == 0) {
            val resource = safeApiCall { dataSource.callChatListUserInfo(userId) }

            if (resource is Resource.Success) {
                if (resource.data.success) {
                    val userInfo = resource.data.chatListUserInfo

                    val entity = ChatListEntity(
                        userId = userInfo?.userId.orEmpty(),
                        email = userInfo?.email,
                        givenName = userInfo?.givenName,
                        familyName = userInfo?.familyName,
                        name = userInfo?.name,
                        picture = userInfo?.picture,
                        gender = userInfo?.gender,
                        age = userInfo?.age,
                        birthDateString = userInfo?.birthDateString,
                        birthDateLong = userInfo?.birthDateLong,
                        aboutMe = userInfo?.aboutMe,
                        localNatives = userInfo?.localNatives ?: emptyList(),
                        localLearnings = userInfo?.localLearnings ?: emptyList(),
                        messages = messages,
                        dateTimeString = LanguageCenterUtils.getDateTimeFormat(dateTimeLong),
                        dateTimeLong = dateTimeLong,
                    )
                    dataSource.saveChatListEntity(entity)
                }
            }
        } else if (count != null && count > 0) {
            dataSource.updateChatListNewMessage(
                userId = userId.orEmpty(),
                messages = messages,
                dateTimeString = LanguageCenterUtils.getDateTimeFormat(dateTimeLong),
                dateTimeLong = dateTimeLong,
            )
        }
    }

}
