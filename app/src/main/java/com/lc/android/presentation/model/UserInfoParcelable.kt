package com.lc.android.presentation.model

import android.os.Parcel
import android.os.Parcelable

data class UserInfoParcelable(
    val userId: String? = null,
    val email: String? = null,
    val name: String? = null,
    val picture: String? = null,
    val gender: String? = null,
    val age: Int? = null,
    val aboutMe: String? = null,
    val algorithm: String? = null,
    val localNatives: List<UserInfoLocaleParcelable>? = emptyList(),
    val localLearnings: List<UserInfoLocaleParcelable>? = emptyList(),
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.createTypedArrayList(UserInfoLocaleParcelable),
        parcel.createTypedArrayList(UserInfoLocaleParcelable)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(userId)
        parcel.writeString(email)
        parcel.writeString(name)
        parcel.writeString(picture)
        parcel.writeString(gender)
        parcel.writeValue(age)
        parcel.writeString(aboutMe)
        parcel.writeString(algorithm)
        parcel.writeTypedList(localNatives)
        parcel.writeTypedList(localLearnings)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserInfoParcelable> {
        override fun createFromParcel(parcel: Parcel): UserInfoParcelable {
            return UserInfoParcelable(parcel)
        }

        override fun newArray(size: Int): Array<UserInfoParcelable?> {
            return arrayOfNulls(size)
        }
    }
}
