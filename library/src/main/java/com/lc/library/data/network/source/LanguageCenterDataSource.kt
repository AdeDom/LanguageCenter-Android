package com.lc.library.data.network.source

import com.lc.library.data.db.entities.*
import com.lc.library.data.model.GoogleTranslateResponse
import com.lc.server.models.model.TalkSendMessageWebSocket
import com.lc.server.models.request.*
import com.lc.server.models.response.*
import kotlinx.coroutines.flow.Flow

interface LanguageCenterDataSource {

    suspend fun saveUserInfo(userInfo: UserInfoEntity)

    suspend fun getDbUserInfo(): UserInfoEntity?

    fun getDbUserInfoFlow(): Flow<UserInfoEntity>

    suspend fun deleteUserInfo()

    suspend fun saveFriendInfo(friendInfoEntity: FriendInfoEntity)

    suspend fun getDbFriendInfoList(): List<FriendInfoEntity>?

    fun getDbFriendInfoFlow(): Flow<List<FriendInfoEntity>>

    suspend fun deleteFriendInfo()

    suspend fun saveAddChatGroupDetail(addChatGroupDetailEntity: AddChatGroupDetailEntity)

    suspend fun getDbAddChatGroupDetailBySearch(search: String?): List<AddChatGroupDetailEntity>?

    suspend fun getDbAddChatGroupDetailList(): List<AddChatGroupDetailEntity>?

    suspend fun deleteAddChatGroupDetail(userId: String?)

    suspend fun deleteAllAddChatGroupDetail()

    suspend fun saveTalk(talkEntity: TalkEntity)

    suspend fun getDbTalkByTalkId(talkId: String): TalkEntity?

    fun getDbTalkByOtherUserIdFlow(otherUserId: String?): Flow<List<TalkEntity>>

    suspend fun getDbCountTalkByTalkId(talkId: String): Int

    suspend fun updateTalkSendMessage(
        talkId: String,
        dateString: String,
        timeString: String,
        dateTimeLong: Long,
        isSendMessage: Boolean
    )

    suspend fun deleteTalk()

    suspend fun saveChatListEntity(chatListEntity: ChatListEntity)

    suspend fun getDbChatListAll(): List<ChatListEntity>

    fun getDbChatListFlow(): Flow<List<ChatListEntity>>

    suspend fun getDbChatListBySearch(search: String?): List<ChatListEntity>?

    suspend fun getDbChatListCountByUserId(userId: String): Int

    suspend fun updateChatListNewMessage(
        userId: String,
        messages: String,
        dateTimeString: String,
        dateTimeLong: Long
    )

    suspend fun deleteChatList()

    suspend fun saveVocabularyFeedback(entity: VocabularyFeedbackEntity)

    suspend fun getDbVocabularyIsEvaluation(vocabularyId: String): Int

    suspend fun getDbVocabularyFeedback(): VocabularyFeedbackEntity?

    suspend fun updateVocabularyFeedbackIsEvaluation(vocabularyId: String)

    suspend fun callSignIn(request: SignInRequest): SignInResponse

    suspend fun callValidateToken(refreshToken: String?): BaseResponse

    suspend fun callFetchUserInfo(): UserInfoResponse

    suspend fun callGuideUpdateProfile(guideUpdateProfileRequest: GuideUpdateProfileRequest): BaseResponse

    suspend fun callEditProfile(editProfileRequest: EditProfileRequest): BaseResponse

    suspend fun callEditLocale(editLocaleRequest: EditLocaleRequest): BaseResponse

    suspend fun callFetchFriendInfo(): FetchFriendInfoResponse

    suspend fun callFetchCommunity(): FetchCommunityResponse

    suspend fun callAddAlgorithm(addAlgorithmRequest: AddAlgorithmRequest): BaseResponse

    suspend fun callSendMessage(sendMessageRequest: SendMessageRequest): SendMessageResponse

    suspend fun callChatListUserInfo(otherUserId: String?): ChatListUserInfoResponse

    suspend fun callReadMessages(readUserId: String?): BaseResponse

    suspend fun callReceiveMessage(talkId: String?): BaseResponse

    suspend fun callResendMessage(resendMessageRequest: ResendMessageRequest): BaseResponse

    suspend fun callFetchTalkUnreceived(): FetchTalkUnreceivedResponse

    suspend fun callUpdateReceiveMessages(updateReceiveMessageRequest: UpdateReceiveMessageRequest): BaseResponse

    suspend fun callLanguageCenterTranslate(vocabulary: String?): LanguageCenterTranslateResponse

    suspend fun callAddChatGroup(addChatGroupRequest: AddChatGroupRequest): BaseResponse

    suspend fun callFetchChatGroup(): FetchChatGroupResponse

    suspend fun callFetchChatGroupDetail(chatGroupId: Int?): FetchChatGroupDetailResponse

    suspend fun callRenameChatGroup(renameChatGroupRequest: RenameChatGroupRequest): BaseResponse

    suspend fun callRemoveChatGroup(chatGroupId: Int?): BaseResponse

    suspend fun callFetchAddChatGroupDetail(): FetchAddChatGroupDetailResponse

    suspend fun callChangeChatGroup(changeChatGroupRequest: ChangeChatGroupRequest): BaseResponse

    suspend fun callRemoveChatGroupDetail(removeChatGroupDetailRequest: RemoveChatGroupDetailRequest): BaseResponse

    suspend fun callAddChatGroupFriend(addChatGroupFriendRequest: AddChatGroupFriendRequest): BaseResponse

    suspend fun callAddVocabularyTranslation(addVocabularyTranslationRequest: AddVocabularyTranslationRequest): BaseResponse

    suspend fun callFetchVocabularyGroup(): FetchVocabularyGroupResponse

    suspend fun callFetchVocabularyDetail(vocabularyGroupId: Int): FetchVocabularyDetailResponse

    suspend fun callVocabularyTranslationFeedback(vocabularyTranslationFeedbackRequest: VocabularyTranslationFeedbackRequest): BaseResponse

    suspend fun callGoogleTranslate(
        vocabulary: String,
        source: String,
        target: String,
    ): GoogleTranslateResponse

    suspend fun incomingSendMessageSocket(listener: suspend (TalkSendMessageWebSocket) -> Unit)

    suspend fun outgoingSendMessageSocket(talkSendMessageWebSocket: TalkSendMessageWebSocket): Unit?

}
