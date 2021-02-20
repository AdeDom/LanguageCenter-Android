package com.lc.library.data.db.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.lc.server.models.model.Translation

class TranslationConverter {

    @TypeConverter
    fun listToJson(value: List<Translation>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String): List<Translation> {
        return Gson().fromJson(value, Array<Translation>::class.java).toList()
    }

}
