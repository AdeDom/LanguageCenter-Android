package com.lc.library.data.network.api

import com.lc.server.models.request.*
import com.lc.server.models.response.BaseResponse
import com.lc.server.models.response.FetchCommunityResponse
import com.lc.server.models.response.SignInResponse
import com.lc.server.models.response.UserInfoResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

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

    // TODO: 06/01/2564 rename route to chat group controller
    @POST("api/chat/add-chat-group-new")
    suspend fun callAddChatGroupNew(@Body addChatGroupNewRequest: AddChatGroupNewRequest): BaseResponse

}
