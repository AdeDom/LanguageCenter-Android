package com.lc.android.presentation.model

import android.os.Parcel
import android.os.Parcelable

data class UserInfoLocaleParcelable(
    val locale: String? = null,
    val level: Int = 0,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(locale)
        parcel.writeInt(level)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserInfoLocaleParcelable> {
        override fun createFromParcel(parcel: Parcel): UserInfoLocaleParcelable {
            return UserInfoLocaleParcelable(parcel)
        }

        override fun newArray(size: Int): Array<UserInfoLocaleParcelable?> {
            return arrayOfNulls(size)
        }
    }
}
