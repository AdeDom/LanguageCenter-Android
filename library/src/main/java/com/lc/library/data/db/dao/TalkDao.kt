package com.lc.library.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lc.library.data.db.entities.TalkEntity

@Dao
interface TalkDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTalk(talkEntity: TalkEntity)

    @Query("SELECT * FROM talk WHERE from_user_id = :otherUserId OR to_user_id = :otherUserId")
    fun getDbTalkByOtherUserIdLiveData(otherUserId: String?): LiveData<List<TalkEntity>>

    @Query("DELETE FROM talk")
    suspend fun deleteTalk()

}
