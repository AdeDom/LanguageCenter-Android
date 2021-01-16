package com.lc.library.data.network.api

import com.lc.server.models.request.*
import com.lc.server.models.response.*
import retrofit2.http.*

interface LanguageCenterApi {

    @POST("api/auth/sign-in")
    suspend fun callSignIn(@Body request: SignInRequest): SignInResponse

    @POST("api/auth/refresh-token")
    suspend fun callRefreshToken(@Body refreshToken: RefreshTokenRequest): SignInResponse

    @GET("api/account/user-info")
    suspend fun callFetchUserInfo(): UserInfoResponse

    @PUT("api/account/guide-update-profile")
    suspend fun callGuideUpdateProfile(@Body guideUpdateProfileRequest: GuideUpdateProfileRequest): BaseResponse

    @PUT("api/account/edit-profile")
    suspend fun callEditProfile(@Body editProfileRequest: EditProfileRequest): BaseResponse

    @PUT("api/account/edit-locale")
    suspend fun callEditLocale(@Body editLocaleRequest: EditLocaleRequest): BaseResponse

    @GET("api/community/fetch-community")
    suspend fun callFetchCommunity(): FetchCommunityResponse

    @POST("api/community/add-algorithm")
    suspend fun callAddAlgorithm(@Body addAlgorithmRequest: AddAlgorithmRequest): BaseResponse

    @POST("api/chat-group/add-chat-group-new")
    suspend fun callAddChatGroupNew(@Body addChatGroupNewRequest: AddChatGroupNewRequest): BaseResponse

    @POST("api/chat-group/add-chat-group")
    suspend fun callAddChatGroup(@Body addChatGroupRequest: AddChatGroupRequest): BaseResponse

    @GET("api/chat-group/fetch-chat-group")
    suspend fun callFetchChatGroup(): FetchChatGroupResponse

    @GET("api/chat-group/fetch-chat-group-detail/{chatGroupId}")
    suspend fun callFetchChatGroupDetail(@Path("chatGroupId") chatGroupId: Int?): FetchChatGroupDetailResponse

    @PUT("api/chat-group/rename-chat-group")
    suspend fun callRenameChatGroup(@Body renameChatGroupRequest: RenameChatGroupRequest): BaseResponse

    @DELETE("api/chat-group/remove-chat-group/{chatGroupId}")
    suspend fun callRemoveChatGroup(@Path("chatGroupId") chatGroupId: Int?): BaseResponse

    @GET("api/chat-group/fetch-add-chat-group-detail")
    suspend fun callFetchAddChatGroupDetail(@Query("userId") userId: String?): FetchAddChatGroupDetailResponse

    @POST("api/chat-group/add-chat-group-detail")
    suspend fun callAddChatGroupDetail(@Body addChatGroupDetailRequest: AddChatGroupDetailRequest): BaseResponse

    @PUT("api/chat-group/change-chat-group")
    suspend fun callChangeChatGroup(@Body changeChatGroupRequest: ChangeChatGroupRequest): BaseResponse

    @HTTP(method = "DELETE", path = "api/chat-group/remove-chat-group-detail", hasBody = true)
    suspend fun callRemoveChatGroupDetail(@Body removeChatGroupDetailRequest: RemoveChatGroupDetailRequest): BaseResponse

}
