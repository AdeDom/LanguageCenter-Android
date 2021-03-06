package com.lc.library.data.network.api

import com.lc.server.models.request.*
import com.lc.server.models.response.*
import retrofit2.http.*

interface LanguageCenterApi {

    @POST("api/auth/sign-in")
    suspend fun callSignIn(@Body request: SignInRequest): SignInResponse

    @POST("api/auth/refresh-token")
    suspend fun callRefreshToken(@Body refreshToken: RefreshTokenRequest): SignInResponse

    @GET("api/auth/validate-token/token")
    suspend fun callValidateToken(@Query("refreshToken") refreshToken: String?): BaseResponse

    @GET("api/account/user-info")
    suspend fun callFetchUserInfo(): UserInfoResponse

    @PUT("api/account/guide-update-profile")
    suspend fun callGuideUpdateProfile(@Body guideUpdateProfileRequest: GuideUpdateProfileRequest): BaseResponse

    @PUT("api/account/edit-profile")
    suspend fun callEditProfile(@Body editProfileRequest: EditProfileRequest): BaseResponse

    @PUT("api/account/edit-locale")
    suspend fun callEditLocale(@Body editLocaleRequest: EditLocaleRequest): BaseResponse

    @GET("api/account/fetch-friend-info")
    suspend fun callFetchFriendInfo(): FetchFriendInfoResponse

    @GET("api/community/fetch-community")
    suspend fun callFetchCommunity(): FetchCommunityResponse

    @POST("api/community/add-algorithm")
    suspend fun callAddAlgorithm(@Body addAlgorithmRequest: AddAlgorithmRequest): BaseResponse

    @POST("api/chats/send-message")
    suspend fun callSendMessage(@Body sendMessageRequest: SendMessageRequest): SendMessageResponse

    @GET("api/chats/chatListUserInfo")
    suspend fun callChatListUserInfo(@Query("otherUserId") otherUserId: String?): ChatListUserInfoResponse

    @PATCH("api/chats/read-messages/{readUserId}")
    suspend fun callReadMessages(@Path("readUserId") readUserId: String?): BaseResponse

    @PATCH("api/chats/receive-message/{talkId}")
    suspend fun callReceiveMessage(@Path("talkId") talkId: String?): BaseResponse

    @POST("api/chats/resend-message")
    suspend fun callResendMessage(@Body resendMessageRequest: ResendMessageRequest): BaseResponse

    @GET("api/chats/fetch-talk-unreceived")
    suspend fun callFetchTalkUnreceived(): FetchTalkUnreceivedResponse

    @PUT("api/chats/update-receive-messages")
    suspend fun callUpdateReceiveMessages(@Body updateReceiveMessageRequest: UpdateReceiveMessageRequest): BaseResponse

    @GET("api/chats/languageCenter/translate")
    suspend fun callLanguageCenterTranslate(@Query("vocabulary") vocabulary: String?): LanguageCenterTranslateResponse

    @POST("api/friend-group/add-chat-group")
    suspend fun callAddChatGroup(@Body addChatGroupRequest: AddChatGroupRequest): BaseResponse

    @GET("api/friend-group/fetch-chat-group")
    suspend fun callFetchChatGroup(): FetchChatGroupResponse

    @GET("api/friend-group/fetchChatGroupDetail")
    suspend fun callFetchChatGroupDetail(@Query("chatGroupId") chatGroupId: Int?): FetchChatGroupDetailResponse

    @PUT("api/friend-group/rename-chat-group")
    suspend fun callRenameChatGroup(@Body renameChatGroupRequest: RenameChatGroupRequest): BaseResponse

    @DELETE("api/friend-group/remove-chat-group/{chatGroupId}")
    suspend fun callRemoveChatGroup(@Path("chatGroupId") chatGroupId: Int?): BaseResponse

    @GET("api/friend-group/fetch-add-chat-group-detail")
    suspend fun callFetchAddChatGroupDetail(): FetchAddChatGroupDetailResponse

    @PUT("api/friend-group/change-chat-group")
    suspend fun callChangeChatGroup(@Body changeChatGroupRequest: ChangeChatGroupRequest): BaseResponse

    @HTTP(method = "DELETE", path = "api/friend-group/remove-chat-group-detail", hasBody = true)
    suspend fun callRemoveChatGroupDetail(@Body removeChatGroupDetailRequest: RemoveChatGroupDetailRequest): BaseResponse

    @POST("api/friend-group/add-chat-group-friend")
    suspend fun callAddChatGroupFriend(@Body addChatGroupFriendRequest: AddChatGroupFriendRequest): BaseResponse

    @POST("api/vocabulary/add-vocabulary-translation")
    suspend fun callAddVocabularyTranslation(@Body addVocabularyTranslationRequest: AddVocabularyTranslationRequest): BaseResponse

    @GET("api/vocabulary/fetch-vocabulary-group")
    suspend fun callFetchVocabularyGroup(): FetchVocabularyGroupResponse

    @GET("api/vocabulary/fetch-vocabulary-detail/vocabularyGroup")
    suspend fun callFetchVocabularyDetail(@Query("vocabularyGroupId") vocabularyGroupId: Int): FetchVocabularyDetailResponse

    @POST("api/vocabulary/vocabulary-translation-feedback")
    suspend fun callVocabularyTranslationFeedback(@Body vocabularyTranslationFeedbackRequest: VocabularyTranslationFeedbackRequest): BaseResponse

}
