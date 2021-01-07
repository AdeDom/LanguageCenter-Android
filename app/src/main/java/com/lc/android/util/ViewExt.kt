package com.lc.android.util

import android.view.View
import androidx.annotation.CheckResult
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

fun View.snackbar(message: String?, duration: Int = Snackbar.LENGTH_INDEFINITE) =
    Snackbar.make(this, message.toString(), duration).apply {
        setAction(android.R.string.ok) {
        }
    }.show()

@CheckResult
@ExperimentalCoroutinesApi
fun View.clicks(): Flow<Unit> {
    return callbackFlow {
        setOnClickListener { offer(Unit) }
        awaitClose { setOnClickListener(null) }
    }
}
