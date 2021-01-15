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

    @Query("SELECT * FROM add_chat_group_detail")
    suspend fun getDbAddChatGroupDetail(): List<AddChatGroupDetailEntity>?

    @Query("SELECT * FROM add_chat_group_detail")
    fun getDbAddChatGroupDetailLiveData(): LiveData<List<AddChatGroupDetailEntity>>

    @Query("DELETE FROM add_chat_group_detail")
    suspend fun deleteAddChatGroupDetail()

}
