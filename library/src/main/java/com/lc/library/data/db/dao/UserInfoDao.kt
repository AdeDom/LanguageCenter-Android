package com.lc.library.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lc.library.data.db.entities.UserInfoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUserInfo(userInfo: UserInfoEntity)

    @Query("SELECT * FROM user_info")
    suspend fun getDbUserInfo(): UserInfoEntity?

    @Query("SELECT * FROM user_info")
    fun getDbUserInfoFlow(): Flow<UserInfoEntity>

    @Query("DELETE FROM user_info")
    suspend fun deleteUserInfo()

}
