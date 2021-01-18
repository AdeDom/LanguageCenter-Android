package com.lc.library.domain.repository

import com.lc.library.data.db.entities.FriendInfoEntity
import com.lc.library.data.repository.Resource
import com.lc.server.models.request.*
import com.lc.server.models.response.*

interface LanguageCenterRepository {

    suspend fun callSignIn(request: SignInRequest): Resource<SignInResponse>

    suspend fun signOut()

    suspend fun callGuideUpdateProfile(guideUpdateProfileRequest: GuideUpdateProfileRequest): Resource<BaseResponse>

    suspend fun callEditProfile(editProfileRequest: EditProfileRequest): Resource<BaseResponse>

    suspend fun callEditLocale(editLocaleRequest: EditLocaleRequest): Resource<BaseResponse>

    suspend fun callFetchCommunity(): Resource<FetchCommunityResponse>

    suspend fun callAddAlgorithm(addAlgorithmRequest: AddAlgorithmRequest): Resource<BaseResponse>

    suspend fun callSendMessage(sendMessageRequest: SendMessageRequest): Resource<BaseResponse>

    suspend fun callAddChatGroup(addChatGroupRequest: AddChatGroupRequest): Resource<BaseResponse>

    suspend fun callFetchChatGroup(): Resource<FetchChatGroupResponse>

    suspend fun callFetchChatGroupDetail(chatGroupId: Int?): Resource<FetchChatGroupDetailResponse>

    suspend fun callRenameChatGroup(renameChatGroupRequest: RenameChatGroupRequest): Resource<BaseResponse>

    suspend fun callRemoveChatGroup(chatGroupId: Int?): Resource<BaseResponse>

    suspend fun callFetchAddChatGroupDetail(): Resource<FetchAddChatGroupDetailResponse>

    suspend fun callChangeChatGroup(changeChatGroupRequest: ChangeChatGroupRequest): Resource<BaseResponse>

    suspend fun callRemoveChatGroupDetail(removeChatGroupDetailRequest: RemoveChatGroupDetailRequest): Resource<BaseResponse>

    suspend fun callAddChatGroupFriend(
        chatGroupId: Int?,
        friendId: String?,
        friendInfoEntity: FriendInfoEntity,
    ): Resource<BaseResponse>

}
