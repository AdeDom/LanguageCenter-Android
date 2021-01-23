package com.lc.library.data.repository

import com.lc.library.data.db.entities.*
import com.lc.library.data.model.ResendMessageRequest
import com.lc.library.data.network.source.LanguageCenterDataSource
import com.lc.library.domain.repository.LanguageCenterRepository
import com.lc.library.sharedpreference.pref.PreferenceAuth
import com.lc.library.util.LanguageCenterUtils
import com.lc.server.models.model.AddChatGroupDetail
import com.lc.server.models.model.TalkSendMessageWebSocket
import com.lc.server.models.request.*
import com.lc.server.models.response.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class LanguageCenterRepositoryImpl(
    private val dataSource: LanguageCenterDataSource,
    private val pref: PreferenceAuth,
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

    private fun saveTokenAuth(response: SignInResponse) {
        if (response.success) {
            pref.accessToken = response.token?.accessToken.orEmpty()
            pref.refreshToken = response.token?.refreshToken.orEmpty()
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
        pref.accessToken = ""
        pref.refreshToken = ""
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
        val talkId = LanguageCenterUtils.getRandomUUID()
        val fromUserId = dataSource.getDbUserInfo()?.userId
        val dateTimeLong = LanguageCenterUtils.getCurrentTimeMillis()
        val entity = TalkEntity(
            talkId = talkId,
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

        val request = sendMessageRequest.copy(talkId = talkId)
        val resource = safeApiCall { dataSource.callSendMessage(request) }

        if (resource is Resource.Success) {
            if (resource.data.success) {
                webSocketsSendMessage(
                    talkId,
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

        // update send message
        dataSource.updateTalkSendMessage(
            talkId = talkId.orEmpty(),
            dateString = LanguageCenterUtils.getDateFormat(dateTimeLong ?: 0),
            timeString = LanguageCenterUtils.getTimeFormat(dateTimeLong ?: 0),
            dateTimeLong = dateTimeLong ?: 0,
            isSendMessage = true,
        )

        // update chat list
        return dataSource.updateChatListNewMessage(
            userId = toUserId.orEmpty(),
            messages = messages.orEmpty(),
            dateTimeString = LanguageCenterUtils.getDateTimeFormat(dateTimeLong ?: 0),
            dateTimeLong = dateTimeLong ?: 0,
        )
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

    override suspend fun incomingSendMessageSocket() {
        dataSource.incomingSendMessageSocket {
            val count = dataSource.getDbCountTalkByTalkId(it.talkId)
            if (count == 0) {
                sendMessageCompleted(it)
                callReceiveMessage(it)
                saveChatListDatabase(it)
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

    private suspend fun callReceiveMessage(socket: TalkSendMessageWebSocket) {
        dataSource.callReceiveMessage(socket.talkId)
    }

    private suspend fun saveChatListDatabase(socket: TalkSendMessageWebSocket) {
        val getDbUserInfoUserId = dataSource.getDbUserInfo()?.userId

        val listUserId = mutableListOf<String>()
        listUserId.add(socket.fromUserId)
        listUserId.add(socket.toUserId)
        val userId = listUserId.singleOrNull { it != getDbUserInfoUserId }

        val count = dataSource.getDbChatListCountByUserId(userId)
        if (count == 0) {
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
                        messages = socket.messages,
                        dateTimeString = LanguageCenterUtils.getDateTimeFormat(socket.dateTimeLong),
                        dateTimeLong = socket.dateTimeLong,
                    )
                    dataSource.saveChatListEntity(entity)
                }
            }
        } else {
            dataSource.updateChatListNewMessage(
                userId = userId.orEmpty(),
                messages = socket.messages,
                dateTimeString = LanguageCenterUtils.getDateTimeFormat(socket.dateTimeLong),
                dateTimeLong = socket.dateTimeLong,
            )
        }
    }

}
