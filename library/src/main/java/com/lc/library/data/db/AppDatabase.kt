package com.lc.library.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.lc.library.data.db.converter.UserInfoLocaleConverters
import com.lc.library.data.db.dao.AddChatGroupDetailDao
import com.lc.library.data.db.dao.FriendInfoDao
import com.lc.library.data.db.dao.UserInfoDao
import com.lc.library.data.db.entities.AddChatGroupDetailEntity
import com.lc.library.data.db.entities.FriendInfoEntity
import com.lc.library.data.db.entities.UserInfoEntity

@Database(
    entities = [UserInfoEntity::class, FriendInfoEntity::class, AddChatGroupDetailEntity::class],
    version = 9
)
@TypeConverters(UserInfoLocaleConverters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getUserInfoDao(): UserInfoDao

    abstract fun getFriendInfoDao(): FriendInfoDao

    abstract fun getAddChatGroupDetailDao(): AddChatGroupDetailDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "LanguageCenter.db"
        ).fallbackToDestructiveMigration().build()
    }

}
