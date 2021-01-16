package com.lc.android.presentation.model

import android.os.Parcel
import android.os.Parcelable

data class ChatGroup(
    val chatGroupId: Int? = null,
    val groupName: String? = null,
    val userId: String? = null,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(chatGroupId)
        parcel.writeString(groupName)
        parcel.writeString(userId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ChatGroup> {
        override fun createFromParcel(parcel: Parcel): ChatGroup {
            return ChatGroup(parcel)
        }

        override fun newArray(size: Int): Array<ChatGroup?> {
            return arrayOfNulls(size)
        }
    }
}
