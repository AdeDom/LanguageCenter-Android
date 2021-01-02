package com.lc.android.base

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.lc.android.util.toast
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch

abstract class BaseActivity : AppCompatActivity() {

    protected inline fun <reified T> LiveData<T>.observe(crossinline onNext: (T) -> Unit) {
        observe(this@BaseActivity, { onNext(it) })
    }

    protected fun LiveData<Throwable>.observeError() {
        observe(this@BaseActivity, {
            it.printStackTrace()
            toast("BaseActivity : observeError ${it.message}", Toast.LENGTH_LONG)
        })
    }

    protected inline fun <reified T> Flow<T>.observe(crossinline onNext: (T) -> Unit) {
        this
            .catch { e ->
                toast("BaseActivity : observe ${e.message}", Toast.LENGTH_LONG)
            }
            .asLiveData()
            .observe(this@BaseActivity, { onNext(it) })
    }

}
