package com.lc.library.data.network.api

import com.lc.server.models.request.RefreshTokenRequest
import com.lc.server.models.request.SignInRequest
import com.lc.server.models.response.SignInResponse
import com.lc.server.models.response.UserInfoResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface LanguageCenterApi {

    @POST("api/auth/sign-in")
    suspend fun callSignIn(@Body request: SignInRequest): SignInResponse

    @POST("api/auth/refresh-token")
    suspend fun callRefreshToken(@Body refreshToken: RefreshTokenRequest): SignInResponse

    @GET("api/account/user-info")
    suspend fun callFetchUserInfo(): UserInfoResponse

}
