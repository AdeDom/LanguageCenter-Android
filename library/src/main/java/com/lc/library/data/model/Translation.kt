package com.lc.library.data.model

import com.google.gson.annotations.SerializedName

data class Translation(
    @SerializedName("translatedText") val translatedText: String? = null,
)
