package com.lc.library.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "talk")
data class TalkEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "talk_id") val talkId: String,
    @ColumnInfo(name = "from_user_id") val fromUserId: String,
    @ColumnInfo(name = "to_user_id") val toUserId: String,
    @ColumnInfo(name = "messages") val messages: String,
    @ColumnInfo(name = "date_string") val dateString: String,
    @ColumnInfo(name = "time_string") val timeString: String,
    @ColumnInfo(name = "date_time_long") val dateTimeLong: Long,
    @ColumnInfo(name = "is_read") val isRead: Boolean,
    @ColumnInfo(name = "is_send_message") val isSendMessage: Boolean,
    @ColumnInfo(name = "is_send_type") val isSendType: Boolean,
)
