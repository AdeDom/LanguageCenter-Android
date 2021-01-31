package com.lc.library.data.model

import com.google.gson.annotations.SerializedName

data class GoogleTranslateResponse(
    @SerializedName("data") val `data`: Data? = null,
)
