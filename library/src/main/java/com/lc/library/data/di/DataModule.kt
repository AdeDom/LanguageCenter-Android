package com.lc.library.data.di

import com.lc.library.data.db.AppDatabase
import com.lc.library.data.network.source.DataSourceProvider
import com.lc.library.data.network.source.LanguageCenterDataSource
import com.lc.library.data.network.source.LanguageCenterDataSourceImpl
import com.lc.library.data.repository.LanguageCenterRepositoryImpl
import com.lc.library.domain.repository.LanguageCenterRepository
import org.koin.dsl.module

private val dataModule = module {

    single { AppDatabase(get()) }

    single { DataSourceProvider(get()) }

    single<LanguageCenterDataSource> { LanguageCenterDataSourceImpl(get(), get()) }

    single<LanguageCenterRepository> { LanguageCenterRepositoryImpl(get(), get()) }

}

val getDataAndroidModule = dataModule
