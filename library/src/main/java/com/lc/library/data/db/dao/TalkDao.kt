package com.lc.library.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lc.library.data.db.entities.TalkEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TalkDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTalk(talkEntity: TalkEntity)

    @Query("SELECT * FROM talk WHERE talk_id = :talkId")
    suspend fun getDbTalkByTalkId(talkId: String): TalkEntity?

    @Query("SELECT * FROM talk WHERE from_user_id = :otherUserId OR to_user_id = :otherUserId ORDER BY date_time_long ASC")
    fun getDbTalkByOtherUserIdFlow(otherUserId: String?): Flow<List<TalkEntity>>

    @Query("SELECT COUNT(*) FROM talk WHERE talk_id = :talkId")
    suspend fun getDbCountTalkByTalkId(talkId: String): Int

    @Query("UPDATE talk SET date_string = :dateString, time_string = :timeString, date_time_long = :dateTimeLong, is_send_message = :isSendMessage WHERE talk_id = :talkId")
    suspend fun updateTalkSendMessage(
        talkId: String,
        dateString: String,
        timeString: String,
        dateTimeLong: Long,
        isSendMessage: Boolean
    )

    @Query("DELETE FROM talk")
    suspend fun deleteTalk()

}
