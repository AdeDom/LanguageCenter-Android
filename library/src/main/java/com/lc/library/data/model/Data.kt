package com.lc.library.data.model

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("translations") val translations: List<Translation> = emptyList(),
)
