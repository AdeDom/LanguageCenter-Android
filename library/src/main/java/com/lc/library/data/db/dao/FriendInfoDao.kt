package com.lc.library.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lc.library.data.db.entities.FriendInfoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FriendInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFriendInfo(friendInfoEntity: FriendInfoEntity)

    @Query("SELECT * FROM friend_info")
    suspend fun getDbFriendInfoList(): List<FriendInfoEntity>?

    @Query("SELECT * FROM friend_info")
    fun getDbFriendInfoFlow(): Flow<List<FriendInfoEntity>>

    @Query("DELETE FROM friend_info")
    suspend fun deleteFriendInfo()

}
