package com.lc.library.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lc.library.data.db.entities.ChatListEntity

@Dao
interface ChatListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveChatListEntity(chatListEntity: ChatListEntity)

    @Query("SELECT * FROM chat_list")
    suspend fun getDbChatListAll(): List<ChatListEntity>

    @Query("SELECT * FROM chat_list ORDER BY date_time_long DESC")
    fun getDbChatListLiveData(): LiveData<List<ChatListEntity>>

    @Query("SELECT * FROM chat_list WHERE given_name LIKE '%' || :search || '%'  OR family_name LIKE '%' || :search || '%' ")
    suspend fun getDbChatListBySearch(search: String?): List<ChatListEntity>?

    @Query("SELECT COUNT(*) FROM chat_list WHERE user_id = :userId")
    suspend fun getDbChatListCountByUserId(userId: String): Int

    @Query("UPDATE chat_list SET messages = :messages, date_time_string = :dateTimeString, date_time_long = :dateTimeLong WHERE user_id = :userId")
    suspend fun updateChatListNewMessage(
        userId: String,
        messages: String,
        dateTimeString: String,
        dateTimeLong: Long
    )

    @Query("DELETE FROM chat_list")
    suspend fun deleteChatList()

}
