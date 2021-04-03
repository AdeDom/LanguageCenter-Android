package com.lc.library.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lc.library.data.db.entities.AddChatGroupDetailEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AddChatGroupDetailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAddChatGroupDetail(addChatGroupDetailEntity: AddChatGroupDetailEntity)

    @Query("SELECT user_id FROM add_chat_group_detail ORDER BY created DESC LIMIT 1")
    suspend fun getDbFetchLastUserId(): String?

    @Query(
        "SELECT * FROM add_chat_group_detail WHERE email LIKE '%' || :search || '%' " +
                " OR given_name LIKE '%' || :search || '%' " +
                " OR family_name LIKE '%' || :search || '%' " +
                " OR about_me LIKE '%' || :search || '%'"
    )
    suspend fun getDbAddChatGroupDetailBySearch(search: String?): List<AddChatGroupDetailEntity>?

    @Query("SELECT * FROM add_chat_group_detail")
    suspend fun getDbAddChatGroupDetailList(): List<AddChatGroupDetailEntity>?

    @Query("SELECT * FROM add_chat_group_detail")
    fun getDbAddChatGroupDetailFlow(): Flow<List<AddChatGroupDetailEntity>>

    @Query("DELETE FROM add_chat_group_detail WHERE user_id = :userId")
    suspend fun deleteAddChatGroupDetail(userId: String?)

    @Query("DELETE FROM add_chat_group_detail")
    suspend fun deleteAllAddChatGroupDetail()

}
