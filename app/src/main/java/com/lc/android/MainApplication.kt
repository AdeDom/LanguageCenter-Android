package com.lc.android

import android.app.Application
import com.facebook.stetho.Stetho
import com.lc.android.presentation.di.getPresentationModule
import com.lc.library.data.di.getDataAndroidModule
import com.lc.library.domain.di.getDomainModule
import com.lc.library.sharedpreference.di.getSharedPreferenceModule
import io.ktor.util.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 * AdeDom Jaiyen
 * https://github.com/AdeDom
 */
@FlowPreview
@KtorExperimentalAPI
@ExperimentalCoroutinesApi
class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            koin.loadModules(
                listOf(
                    getPresentationModule,
                    getDomainModule,
                    getDataAndroidModule,
                    getSharedPreferenceModule
                )
            )
            koin.createRootScope()
        }

        Stetho.initializeWithDefaults(this)
    }
}
