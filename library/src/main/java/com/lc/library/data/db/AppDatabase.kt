package com.lc.library.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.lc.library.data.db.converter.UserInfoLocaleConverters
import com.lc.library.data.db.dao.*
import com.lc.library.data.db.entities.*

@Database(
    entities = [UserInfoEntity::class, FriendInfoEntity::class, AddChatGroupDetailEntity::class,
        TalkEntity::class, ChatListEntity::class],
    version = 14
)
@TypeConverters(UserInfoLocaleConverters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getUserInfoDao(): UserInfoDao

    abstract fun getFriendInfoDao(): FriendInfoDao

    abstract fun getAddChatGroupDetailDao(): AddChatGroupDetailDao

    abstract fun getTalkDao(): TalkDao

    abstract fun getChatListDao(): ChatListDao

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
