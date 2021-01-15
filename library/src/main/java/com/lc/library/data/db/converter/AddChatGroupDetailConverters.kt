package com.lc.library.data.db.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.lc.server.models.model.AddChatGroupDetail

class AddChatGroupDetailConverters {

    @TypeConverter
    fun listToJson(value: List<AddChatGroupDetail>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String): List<AddChatGroupDetail> {
        return Gson().fromJson(value, Array<AddChatGroupDetail>::class.java).toList()
    }

}
