package com.lc.library.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lc.library.data.db.entities.TalkEntity

@Dao
interface TalkDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTalk(talkEntity: TalkEntity)

    @Query("UPDATE talk SET is_send_message = 1 WHERE talk_id = :talkId")
    suspend fun updateIsSendMessage(talkId: String)

    @Query("DELETE FROM talk")
    suspend fun deleteTalk()

}
