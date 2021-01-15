package com.lc.library.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lc.server.models.model.AddChatGroupDetail

@Entity(tableName = "add_chat_group_detail")
data class AddChatGroupDetailEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "add_chat_group_detail_id") val addChatGroupDetailId: Int = 0,
    @ColumnInfo(name = "add_chat_group_detail_list") val addChatGroupDetailList: List<AddChatGroupDetail> = emptyList(),
)
