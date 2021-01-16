package com.lc.library.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lc.library.data.db.entities.AddChatGroupDetailEntity

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
    suspend fun getDbAddChatGroupDetail(search: String?): List<AddChatGroupDetailEntity>?

    @Query("SELECT * FROM add_chat_group_detail")
    fun getDbAddChatGroupDetailLiveData(): LiveData<List<AddChatGroupDetailEntity>>

    @Query("DELETE FROM add_chat_group_detail WHERE user_id = :userId")
    suspend fun deleteAddChatGroupDetail(userId: String?)

    @Query("DELETE FROM add_chat_group_detail")
    suspend fun deleteAllAddChatGroupDetail()

}