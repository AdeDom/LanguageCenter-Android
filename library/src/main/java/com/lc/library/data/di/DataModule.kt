package com.lc.library.data.di

import com.lc.library.data.db.AppDatabase
import com.lc.library.data.network.source.DataSourceProvider
import com.lc.library.data.network.source.LanguageCenterDataSource
import com.lc.library.data.network.source.LanguageCenterDataSourceImpl
import com.lc.library.data.network.ws.LanguageCenterWs
import com.lc.library.data.repository.LanguageCenterRepositoryImpl
import com.lc.library.domain.repository.LanguageCenterRepository
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.features.*
import io.ktor.client.features.logging.*
import io.ktor.client.features.websocket.*
import org.koin.dsl.module

val dataModule = module {

    single {
        HttpClient(OkHttp) {
            install(WebSockets)

            install(HttpTimeout) {
                requestTimeoutMillis = 60_000
            }

            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.HEADERS
            }
        }
    }
    single { LanguageCenterWs(get(), get()) }

    single { AppDatabase(get()) }

    single { DataSourceProvider(get(), get()) }

    single<LanguageCenterDataSource> { LanguageCenterDataSourceImpl(get(), get()) }

    single<LanguageCenterRepository> { LanguageCenterRepositoryImpl(get(), get(), get()) }

}
