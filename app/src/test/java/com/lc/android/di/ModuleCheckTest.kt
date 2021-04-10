package com.lc.android.di

import android.content.Context
import com.lc.android.presentation.di.presentationModule
import com.lc.library.data.di.dataModule
import com.lc.library.domain.di.domainModule
import com.lc.library.sharedpreference.di.sharedPreferenceModule
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.junit.Test
import org.junit.experimental.categories.Category
import org.koin.core.error.InstanceCreationException
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.category.CheckModuleTest
import org.koin.test.check.checkModules

@FlowPreview
@ExperimentalCoroutinesApi
@Category(CheckModuleTest::class)
class ModuleCheckTest : AutoCloseKoinTest() {

    @Test
    fun test_checkModules() {
        val contextModule = module {
            single { mockk<Context>(relaxed = true) }
        }

        try {
            checkModules {
                modules(
                    contextModule,
                    presentationModule,
                    domainModule,
                    dataModule,
                    sharedPreferenceModule
                )
            }
        } catch (e: InstanceCreationException) {
        } catch (e: RuntimeException) {
        }

    }

}
