package com.lc.library.data.network.source

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.lc.library.BuildConfig
import com.lc.library.data.network.api.LanguageCenterApi
import com.lc.library.data.network.api.LanguageTranslateApi
import com.lc.library.data.network.ws.LanguageCenterWs
import com.lc.library.sharedpreference.pref.AuthPref
import com.lc.server.models.request.RefreshTokenRequest
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

class DataSourceProvider(
    private val pref: AuthPref,
    private val webSocket: LanguageCenterWs,
) {

    fun getDataSource(): LanguageCenterApi {
        val okHttpClient = OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG) {
                addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
            }

            connectTimeout(60, TimeUnit.SECONDS)
            writeTimeout(60, TimeUnit.SECONDS)
            readTimeout(60, TimeUnit.SECONDS)

            addNetworkInterceptor(StethoInterceptor())
        }.build()

        val retrofit = Retrofit.Builder().apply {
            client(okHttpClient)
            addConverterFactory(GsonConverterFactory.create())
            baseUrl("https://languagecenter.herokuapp.com/")
        }.build()

        return retrofit.create()
    }

    fun getLanguageCenterDataSource(): LanguageCenterApi {
        val okHttpClient = OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG) {
                addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
            }

            connectTimeout(60, TimeUnit.SECONDS)
            writeTimeout(60, TimeUnit.SECONDS)
            readTimeout(60, TimeUnit.SECONDS)

            addInterceptor { chain ->
                var response: Response = addHeaderToProceedRequest(chain)

                if (response.code == 401 || response.code == 403) {
                    callRefreshToken()
                    response = addHeaderToProceedRequest(chain)
                }

                response
            }

            addNetworkInterceptor(StethoInterceptor())
        }.build()

        val retrofit = Retrofit.Builder().apply {
            client(okHttpClient)
            addConverterFactory(GsonConverterFactory.create())
            baseUrl("https://languagecenter.herokuapp.com/")
        }.build()

        return retrofit.create()
    }

    fun getRapidApi(): LanguageTranslateApi {
        val okHttpClient = OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG) {
                addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
            }

            connectTimeout(60, TimeUnit.SECONDS)
            writeTimeout(60, TimeUnit.SECONDS)
            readTimeout(60, TimeUnit.SECONDS)

            addInterceptor {
                val request = it.request()
                    .newBuilder()
                    .addHeader(
                        "x-rapidapi-key",
                        "48c2171a80mshd6eb0e26bdfe6b1p19a080jsn5552fdf81b8d"
                    )
                    .build()

                it.proceed(request)
            }

            addNetworkInterceptor(StethoInterceptor())
        }.build()

        val retrofit = Retrofit.Builder().apply {
            client(okHttpClient)
            addConverterFactory(GsonConverterFactory.create())
            baseUrl("https://google-translate1.p.rapidapi.com/")
        }.build()

        return retrofit.create()
    }

    fun getWebSocketDataSource() = webSocket

    private fun callRefreshToken() {
        try {
            val request = RefreshTokenRequest(pref.refreshToken)
            val response = runBlocking { getDataSource().callRefreshToken(request) }
            if (response.success) {
                pref.accessToken = response.token?.accessToken.orEmpty()
                pref.refreshToken = response.token?.refreshToken.orEmpty()
            }
        } catch (e: HttpException) {
            pref.accessToken = ""
            pref.refreshToken = ""
        }
    }

    private fun addHeaderToProceedRequest(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("Authorization", "Bearer ${pref.accessToken}")
            .build()

        return chain.proceed(request)
    }

}
