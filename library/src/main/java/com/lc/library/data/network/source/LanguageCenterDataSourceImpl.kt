package com.lc.library.data.network.source

import androidx.lifecycle.LiveData
import com.lc.library.data.db.AppDatabase
import com.lc.library.data.db.entities.AddChatGroupDetailEntity
import com.lc.library.data.db.entities.FriendInfoEntity
import com.lc.library.data.db.entities.TalkEntity
import com.lc.library.data.db.entities.UserInfoEntity
import com.lc.server.models.request.*
import com.lc.server.models.response.*

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

    override suspend fun updateIsSendMessage(talkId: String) {
        return db.getTalkDao().updateIsSendMessage(talkId)
    }

    override suspend fun deleteTalk() {
        return db.getTalkDao().deleteTalk()
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

    override suspend fun callSendMessage(sendMessageRequest: SendMessageRequest): BaseResponse {
        return provider.getLanguageCenterDataSource().callSendMessage(sendMessageRequest)
    }

    override suspend fun callAddChatGroupNew(addChatGroupNewRequest: AddChatGroupNewRequest): BaseResponse {
        return provider.getLanguageCenterDataSource().callAddChatGroupNew(addChatGroupNewRequest)
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

    override suspend fun callAddChatGroupDetail(addChatGroupDetailRequest: AddChatGroupDetailRequest): BaseResponse {
        return provider.getLanguageCenterDataSource()
            .callAddChatGroupDetail(addChatGroupDetailRequest)
    }

    override suspend fun callChangeChatGroup(changeChatGroupRequest: ChangeChatGroupRequest): BaseResponse {
        return provider.getLanguageCenterDataSource().callChangeChatGroup(changeChatGroupRequest)
    }

    override suspend fun callRemoveChatGroupDetail(removeChatGroupDetailRequest: RemoveChatGroupDetailRequest): BaseResponse {
        return provider.getLanguageCenterDataSource()
            .callRemoveChatGroupDetail(removeChatGroupDetailRequest)
    }

}
