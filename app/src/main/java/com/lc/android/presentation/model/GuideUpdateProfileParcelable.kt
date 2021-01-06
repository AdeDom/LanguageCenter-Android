package com.lc.android.presentation.model

import android.os.Parcel
import android.os.Parcelable

data class GuideUpdateProfileParcelable(
    val localNatives: List<UserInfoLocaleParcelable>? = emptyList(),
    val localLearnings: List<UserInfoLocaleParcelable>? = emptyList(),
    val gender: String? = null,
    val birthDate: Long? = null,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.createTypedArrayList(UserInfoLocaleParcelable),
        parcel.createTypedArrayList(UserInfoLocaleParcelable),
        parcel.readString(),
        parcel.readValue(Long::class.java.classLoader) as? Long
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(localNatives)
        parcel.writeTypedList(localLearnings)
        parcel.writeString(gender)
        parcel.writeValue(birthDate)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GuideUpdateProfileParcelable> {
        override fun createFromParcel(parcel: Parcel): GuideUpdateProfileParcelable {
            return GuideUpdateProfileParcelable(parcel)
        }

        override fun newArray(size: Int): Array<GuideUpdateProfileParcelable?> {
            return arrayOfNulls(size)
        }
    }
}
