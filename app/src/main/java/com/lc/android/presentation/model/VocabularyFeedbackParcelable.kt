package com.lc.android.presentation.model

import android.os.Parcel
import android.os.Parcelable

data class VocabularyFeedbackParcelable(
    val vocabularyId: String?,
    val vocabulary: String?,
    val rating: Float = 0F,
    val translations: List<TranslationParcelable>? = emptyList(),
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readFloat(),
        parcel.createTypedArrayList(TranslationParcelable)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(vocabularyId)
        parcel.writeString(vocabulary)
        parcel.writeFloat(rating)
        parcel.writeTypedList(translations)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<VocabularyFeedbackParcelable> {
        override fun createFromParcel(parcel: Parcel): VocabularyFeedbackParcelable {
            return VocabularyFeedbackParcelable(parcel)
        }

        override fun newArray(size: Int): Array<VocabularyFeedbackParcelable?> {
            return arrayOfNulls(size)
        }
    }
}
