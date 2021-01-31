package com.lc.library.data.network.api

import com.lc.library.data.model.GoogleTranslateResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LanguageTranslateApi {

    @FormUrlEncoded
    @POST("language/translate/v2")
    suspend fun callGoogleTranslate(
        @Field("q") vocabulary: String,
        @Field("source") source: String,
        @Field("target") target: String,
    ): GoogleTranslateResponse

}
