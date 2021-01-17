package com.lc.library.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lc.library.data.db.entities.FriendInfoEntity

@Dao
interface FriendInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFriendInfo(friendInfoEntity: FriendInfoEntity)

    @Query("SELECT * FROM friend_info")
    suspend fun getDbFriendInfoList(): List<FriendInfoEntity>?

    @Query("SELECT * FROM friend_info")
    fun getDbFriendInfoLiveData(): LiveData<List<FriendInfoEntity>>

    @Query("DELETE FROM friend_info")
    suspend fun deleteFriendInfo()

}
