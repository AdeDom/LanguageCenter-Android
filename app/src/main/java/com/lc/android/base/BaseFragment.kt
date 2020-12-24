package com.lc.android.base

import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.lc.android.util.toast
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch

abstract class BaseFragment(@LayoutRes layout: Int) : Fragment(layout) {

    protected inline fun <reified T> LiveData<T>.observe(crossinline onNext: (T) -> Unit) {
        observe(this@BaseFragment, { onNext(it) })
    }

    protected fun LiveData<Throwable>.observeError() {
        observe(this@BaseFragment, {
            it.printStackTrace()
            context.toast("BaseFragment : observeError ${it.message}", Toast.LENGTH_LONG)
        })
    }

    protected inline fun <reified T> Flow<T>.observe(crossinline onNext: (T) -> Unit) {
        this
            .catch { e ->
                context.toast("BaseFragment : observe ${e.message}", Toast.LENGTH_LONG)
            }
            .asLiveData()
            .observe(this@BaseFragment, { onNext(it) })
    }

}
