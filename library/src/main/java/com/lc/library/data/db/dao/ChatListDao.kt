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

    @Query("SELECT * FROM chat_list")
    fun getDbChatListLiveData(): LiveData<List<ChatListEntity>>

    @Query("DELETE FROM chat_list")
    suspend fun deleteChatList()

}
