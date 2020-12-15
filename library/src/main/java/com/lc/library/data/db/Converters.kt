package com.lc.library.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.lc.server.models.model.UserInfoLocale

class Converters {

    @TypeConverter
    fun listToJson(value: List<UserInfoLocale>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String): List<UserInfoLocale> {
        return Gson().fromJson(value, Array<UserInfoLocale>::class.java).toList()
    }

}
