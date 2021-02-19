package com.lc.library.domain.repository

import com.lc.library.data.db.entities.ChatListEntity
import com.lc.library.data.db.entities.FriendInfoEntity
import com.lc.library.data.repository.Resource
import com.lc.server.models.request.*
import com.lc.server.models.response.*

interface LanguageCenterRepository {

    suspend fun callSignIn(request: SignInRequest): Resource<SignInResponse>

    suspend fun callValidateToken(): Resource<BaseResponse>

    suspend fun signOut()

    suspend fun saveChatListEntity(chatListEntity: ChatListEntity)

    suspend fun callGuideUpdateProfile(guideUpdateProfileRequest: GuideUpdateProfileRequest): Resource<BaseResponse>

    suspend fun callEditProfile(editProfileRequest: EditProfileRequest): Resource<BaseResponse>

    suspend fun callEditLocale(editLocaleRequest: EditLocaleRequest): Resource<BaseResponse>

    suspend fun callFetchCommunity(): Resource<FetchCommunityResponse>

    suspend fun callAddAlgorithm(addAlgorithmRequest: AddAlgorithmRequest): Resource<BaseResponse>

    suspend fun callSendMessage(sendMessageRequest: SendMessageRequest): Resource<SendMessageResponse>?

    suspend fun callReadMessages(readUserId: String?): Resource<BaseResponse>

    suspend fun callResendMessage(resendMessageRequest: ResendMessageRequest): Resource<BaseResponse>?

    suspend fun callFetchTalkUnreceived(): Resource<FetchTalkUnreceivedResponse>

    suspend fun callLanguageCenterTranslate(vocabulary: String?): Resource<LanguageCenterTranslateResponse>

    suspend fun callAddChatGroup(addChatGroupRequest: AddChatGroupRequest): Resource<BaseResponse>

    suspend fun callFetchChatGroup(): Resource<FetchChatGroupResponse>

    suspend fun callFetchChatGroupDetail(chatGroupId: Int?): Resource<FetchChatGroupDetailResponse>

    suspend fun callRenameChatGroup(renameChatGroupRequest: RenameChatGroupRequest): Resource<BaseResponse>

    suspend fun callRemoveChatGroup(chatGroupId: Int?): Resource<BaseResponse>

    suspend fun callFetchAddChatGroupDetail(): Resource<FetchAddChatGroupDetailResponse>

    suspend fun callChangeChatGroup(changeChatGroupRequest: ChangeChatGroupRequest): Resource<BaseResponse>

    suspend fun callRemoveChatGroupDetail(removeChatGroupDetailRequest: RemoveChatGroupDetailRequest): Resource<BaseResponse>

    suspend fun callAddChatGroupFriend(
        addChatGroupFriendRequest: AddChatGroupFriendRequest,
        friendInfoEntity: FriendInfoEntity,
    ): Resource<BaseResponse>

    suspend fun callAddVocabularyTranslation(addVocabularyTranslationRequest: AddVocabularyTranslationRequest): Resource<BaseResponse>

    suspend fun callFetchVocabularyGroup(): Resource<FetchVocabularyGroupResponse>

    suspend fun callFetchVocabularyDetail(vocabularyGroupId: Int): Resource<FetchVocabularyDetailResponse>

    suspend fun incomingSendMessageSocket()

}
