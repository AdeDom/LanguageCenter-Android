package com.lc.android

import android.app.Application
import com.facebook.stetho.Stetho
import com.lc.android.presentation.di.presentationModule
import com.lc.library.data.di.dataModule
import com.lc.library.domain.di.domainModule
import com.lc.library.sharedpreference.di.sharedPreferenceModule
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
@ExperimentalCoroutinesApi
class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            koin.loadModules(
                listOf(
                    presentationModule,
                    domainModule,
                    dataModule,
                    sharedPreferenceModule
                )
            )
            koin.createRootScope()
        }

        Stetho.initializeWithDefaults(this)
    }
}
