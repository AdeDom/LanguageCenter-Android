package com.lc.library.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lc.server.models.model.UserInfoLocale

@Entity(tableName = "add_chat_group_detail")
data class AddChatGroupDetailEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "user_id") val userId: String = "",
    @ColumnInfo(name = "email") val email: String? = null,
    @ColumnInfo(name = "given_name") val givenName: String? = null,
    @ColumnInfo(name = "family_name") val familyName: String? = null,
    @ColumnInfo(name = "name") val name: String? = null,
    @ColumnInfo(name = "picture") val picture: String? = null,
    @ColumnInfo(name = "gender") val gender: String? = null,
    @ColumnInfo(name = "age") val age: Int? = null,
    @ColumnInfo(name = "birth_date_string") val birthDateString: String? = null,
    @ColumnInfo(name = "birth_date_long") val birthDateLong: Long? = null,
    @ColumnInfo(name = "about_me") val aboutMe: String? = null,
    @ColumnInfo(name = "local_natives") val localNatives: List<UserInfoLocale> = emptyList(),
    @ColumnInfo(name = "local_learnings") val localLearnings: List<UserInfoLocale> = emptyList(),
)
