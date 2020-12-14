package com.lc.library.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_info")
data class UserInfoEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "user_id") val userId: String = "",
    @ColumnInfo(name = "email") val email: String? = null,
    @ColumnInfo(name = "given_name") val givenName: String? = null,
    @ColumnInfo(name = "family_name") val familyName: String? = null,
    @ColumnInfo(name = "name") val name: String? = null,
    @ColumnInfo(name = "picture") val picture: String? = null,
    @ColumnInfo(name = "gender") val gender: String? = null,
    @ColumnInfo(name = "birth_date") val birthDate: String? = null,
    @ColumnInfo(name = "verified_email") val verifiedEmail: Boolean = false,
    @ColumnInfo(name = "about_me") val aboutMe: String? = null,
    @ColumnInfo(name = "created") val created: String? = null,
    @ColumnInfo(name = "updated") val updated: String? = null,
)
