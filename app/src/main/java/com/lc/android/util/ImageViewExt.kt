package com.lc.android.util

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.lc.android.R

fun ImageView.load(
    url: String?,
    @DrawableRes placeholder: Int = R.drawable.ic_profile
) {
    Glide.with(this)
        .load(url)
        .apply(RequestOptions.placeholderOf(placeholder))
        .circleCrop()
        .into(this)
}
