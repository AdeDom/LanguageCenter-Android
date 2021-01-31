package com.lc.library.data.network.source

import androidx.lifecycle.LiveData
import com.lc.library.data.db.AppDatabase
import com.lc.library.data.db.entities.*
import com.lc.library.data.model.GoogleTranslateResponse
import com.lc.server.models.model.TalkSendMessageWebSocket
import com.lc.server.models.request.*
import com.lc.server.models.response.*
import io.ktor.util.*

@KtorExperimentalAPI
class LanguageCenterDataSourceImpl(
    private val db: AppDatabase,
    private val provider: DataSourceProvider,
) : LanguageCenterDataSource {

    override suspend fun saveUserInfo(userInfo: UserInfoEntity) {
        return db.getUserInfoDao().saveUserInfo(userInfo)
    }

    override suspend fun getDbUserInfo(): UserInfoEntity? {
        return db.getUserInfoDao().getDbUserInfo()
    }

    override fun getDbUserInfoLiveData(): LiveData<UserInfoEntity> {
        return db.getUserInfoDao().getDbUserInfoLiveData()
    }

    override suspend fun deleteUserInfo() {
        return db.getUserInfoDao().deleteUserInfo()
    }

    override suspend fun saveFriendInfo(friendInfoEntity: FriendInfoEntity) {
        return db.getFriendInfoDao().saveFriendInfo(friendInfoEntity)
    }

    override suspend fun getDbFriendInfoList(): List<FriendInfoEntity>? {
        return db.getFriendInfoDao().getDbFriendInfoList()
    }

    override fun getDbFriendInfoLiveData(): LiveData<List<FriendInfoEntity>> {
        return db.getFriendInfoDao().getDbFriendInfoLiveData()
    }

    override suspend fun deleteFriendInfo() {
        return db.getFriendInfoDao().deleteFriendInfo()
    }

    override suspend fun saveAddChatGroupDetail(addChatGroupDetailEntity: AddChatGroupDetailEntity) {
        return db.getAddChatGroupDetailDao().saveAddChatGroupDetail(addChatGroupDetailEntity)
    }

    override suspend fun getDbFetchLastUserId(): String? {
        return db.getAddChatGroupDetailDao().getDbFetchLastUserId()
    }

    override suspend fun getDbAddChatGroupDetailBySearch(search: String?): List<AddChatGroupDetailEntity>? {
        return db.getAddChatGroupDetailDao().getDbAddChatGroupDetailBySearch(search)
    }

    override suspend fun getDbAddChatGroupDetailList(): List<AddChatGroupDetailEntity>? {
        return db.getAddChatGroupDetailDao().getDbAddChatGroupDetailList()
    }

    override fun getDbAddChatGroupDetailLiveData(): LiveData<List<AddChatGroupDetailEntity>> {
        return db.getAddChatGroupDetailDao().getDbAddChatGroupDetailLiveData()
    }

    override suspend fun deleteAddChatGroupDetail(userId: String?) {
        return db.getAddChatGroupDetailDao().deleteAddChatGroupDetail(userId)
    }

    override suspend fun deleteAllAddChatGroupDetail() {
        return db.getAddChatGroupDetailDao().deleteAllAddChatGroupDetail()
    }

    override suspend fun saveTalk(talkEntity: TalkEntity) {
        return db.getTalkDao().saveTalk(talkEntity)
    }

    override suspend fun getDbTalkByTalkId(talkId: String): TalkEntity? {
        return db.getTalkDao().getDbTalkByTalkId(talkId)
    }

    override fun getDbTalkByOtherUserIdLiveData(otherUserId: String?): LiveData<List<TalkEntity>> {
        return db.getTalkDao().getDbTalkByOtherUserIdLiveData(otherUserId)
    }

    override suspend fun getDbCountTalkByTalkId(talkId: String): Int {
        return db.getTalkDao().getDbCountTalkByTalkId(talkId)
    }

    override suspend fun updateTalkSendMessage(
        talkId: String,
        dateString: String,
        timeString: String,
        dateTimeLong: Long,
        isSendMessage: Boolean
    ) {
        return db.getTalkDao()
            .updateTalkSendMessage(talkId, dateString, timeString, dateTimeLong, isSendMessage)
    }

    override suspend fun deleteTalk() {
        return db.getTalkDao().deleteTalk()
    }

    override suspend fun saveChatListEntity(chatListEntity: ChatListEntity) {
        return db.getChatListDao().saveChatListEntity(chatListEntity)
    }

    override suspend fun getDbChatListAll(): List<ChatListEntity> {
        return db.getChatListDao().getDbChatListAll()
    }

    override fun getDbChatListLiveData(): LiveData<List<ChatListEntity>> {
        return db.getChatListDao().getDbChatListLiveData()
    }

    override suspend fun getDbChatListBySearch(search: String?): List<ChatListEntity>? {
        return db.getChatListDao().getDbChatListBySearch(search)
    }

    override suspend fun getDbChatListCountByUserId(userId: String): Int {
        return db.getChatListDao().getDbChatListCountByUserId(userId)
    }

    override suspend fun updateChatListNewMessage(
        userId: String,
        messages: String,
        dateTimeString: String,
        dateTimeLong: Long
    ) {
        return db.getChatListDao()
            .updateChatListNewMessage(userId, messages, dateTimeString, dateTimeLong)
    }

    override suspend fun deleteChatList() {
        return db.getChatListDao().deleteChatList()
    }

    override suspend fun callSignIn(request: SignInRequest): SignInResponse {
        return provider.getDataSource().callSignIn(request)
    }

    override suspend fun callFetchUserInfo(): UserInfoResponse {
        return provider.getLanguageCenterDataSource().callFetchUserInfo()
    }

    override suspend fun callGuideUpdateProfile(guideUpdateProfileRequest: GuideUpdateProfileRequest): BaseResponse {
        return provider.getLanguageCenterDataSource()
            .callGuideUpdateProfile(guideUpdateProfileRequest)
    }

    override suspend fun callEditProfile(editProfileRequest: EditProfileRequest): BaseResponse {
        return provider.getLanguageCenterDataSource().callEditProfile(editProfileRequest)
    }

    override suspend fun callEditLocale(editLocaleRequest: EditLocaleRequest): BaseResponse {
        return provider.getLanguageCenterDataSource().callEditLocale(editLocaleRequest)
    }

    override suspend fun callFetchFriendInfo(): FetchFriendInfoResponse {
        return provider.getLanguageCenterDataSource().callFetchFriendInfo()
    }

    override suspend fun callFetchCommunity(): FetchCommunityResponse {
        return provider.getLanguageCenterDataSource().callFetchCommunity()
    }

    override suspend fun callAddAlgorithm(addAlgorithmRequest: AddAlgorithmRequest): BaseResponse {
        return provider.getLanguageCenterDataSource().callAddAlgorithm(addAlgorithmRequest)
    }

    override suspend fun callSendMessage(sendMessageRequest: SendMessageRequest): SendMessageResponse {
        return provider.getLanguageCenterDataSource().callSendMessage(sendMessageRequest)
    }

    override suspend fun callChatListUserInfo(otherUserId: String?): ChatListUserInfoResponse {
        return provider.getLanguageCenterDataSource().callChatListUserInfo(otherUserId)
    }

    override suspend fun callReadMessages(readUserId: String?): BaseResponse {
        return provider.getLanguageCenterDataSource().callReadMessages(readUserId)
    }

    override suspend fun callReceiveMessage(talkId: String?): BaseResponse {
        return provider.getLanguageCenterDataSource().callReceiveMessage(talkId)
    }

    override suspend fun callResendMessage(resendMessageRequest: ResendMessageRequest): BaseResponse {
        return provider.getLanguageCenterDataSource().callResendMessage(resendMessageRequest)
    }

    override suspend fun callFetchTalkUnreceived(): FetchTalkUnreceivedResponse {
        return provider.getLanguageCenterDataSource().callFetchTalkUnreceived()
    }

    override suspend fun callUpdateReceiveMessages(updateReceiveMessageRequest: UpdateReceiveMessageRequest): BaseResponse {
        return provider.getLanguageCenterDataSource()
            .callUpdateReceiveMessages(updateReceiveMessageRequest)
    }

    override suspend fun callLanguageCenterTranslate(vocabulary: String?): LanguageCenterTranslateResponse {
        return provider.getLanguageCenterDataSource().callLanguageCenterTranslate(vocabulary)
    }

    override suspend fun callAddChatGroup(addChatGroupRequest: AddChatGroupRequest): BaseResponse {
        return provider.getLanguageCenterDataSource().callAddChatGroup(addChatGroupRequest)
    }

    override suspend fun callFetchChatGroup(): FetchChatGroupResponse {
        return provider.getLanguageCenterDataSource().callFetchChatGroup()
    }

    override suspend fun callFetchChatGroupDetail(chatGroupId: Int?): FetchChatGroupDetailResponse {
        return provider.getLanguageCenterDataSource().callFetchChatGroupDetail(chatGroupId)
    }

    override suspend fun callRenameChatGroup(renameChatGroupRequest: RenameChatGroupRequest): BaseResponse {
        return provider.getLanguageCenterDataSource().callRenameChatGroup(renameChatGroupRequest)
    }

    override suspend fun callRemoveChatGroup(chatGroupId: Int?): BaseResponse {
        return provider.getLanguageCenterDataSource().callRemoveChatGroup(chatGroupId)
    }

    override suspend fun callFetchAddChatGroupDetail(): FetchAddChatGroupDetailResponse {
        return provider.getLanguageCenterDataSource().callFetchAddChatGroupDetail()
    }

    override suspend fun callChangeChatGroup(changeChatGroupRequest: ChangeChatGroupRequest): BaseResponse {
        return provider.getLanguageCenterDataSource().callChangeChatGroup(changeChatGroupRequest)
    }

    override suspend fun callRemoveChatGroupDetail(removeChatGroupDetailRequest: RemoveChatGroupDetailRequest): BaseResponse {
        return provider.getLanguageCenterDataSource()
            .callRemoveChatGroupDetail(removeChatGroupDetailRequest)
    }

    override suspend fun callAddChatGroupFriend(addChatGroupFriendRequest: AddChatGroupFriendRequest): BaseResponse {
        return provider.getLanguageCenterDataSource()
            .callAddChatGroupFriend(addChatGroupFriendRequest)
    }

    override suspend fun callFetchVocabularyTranslation(): FetchVocabularyTranslationResponse {
        return provider.getLanguageCenterDataSource().callFetchVocabularyTranslation()
    }

    override suspend fun callAddVocabularyTranslation(addVocabularyTranslationRequest: AddVocabularyTranslationRequest): BaseResponse {
        return provider.getLanguageCenterDataSource()
            .callAddVocabularyTranslation(addVocabularyTranslationRequest)
    }

    override suspend fun callGoogleTranslate(
        vocabulary: String,
        source: String,
        target: String
    ): GoogleTranslateResponse {
        return provider.getRapidApi().callGoogleTranslate(vocabulary, source, target)
    }

    override suspend fun incomingSendMessageSocket(listener: suspend (TalkSendMessageWebSocket) -> Unit) {
        return provider.getWebSocketDataSource().incomingSendMessageSocket(listener)
    }

    override suspend fun outgoingSendMessageSocket(talkSendMessageWebSocket: TalkSendMessageWebSocket): Unit? {
        return provider.getWebSocketDataSource().outgoingSendMessageSocket(talkSendMessageWebSocket)
    }

}
