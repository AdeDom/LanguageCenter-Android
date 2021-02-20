package com.lc.android.presentation.model

import android.os.Parcel
import android.os.Parcelable

data class TranslationParcelable(
    val translationId: Int,
    val translation: String?,
    val isCorrect: Boolean? = null,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(translationId)
        parcel.writeString(translation)
        parcel.writeValue(isCorrect)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TranslationParcelable> {
        override fun createFromParcel(parcel: Parcel): TranslationParcelable {
            return TranslationParcelable(parcel)
        }

        override fun newArray(size: Int): Array<TranslationParcelable?> {
            return arrayOfNulls(size)
        }
    }
}
