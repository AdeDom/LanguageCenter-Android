package com.lc.library.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.lc.library.data.db.dao.UserInfoDao
import com.lc.library.data.db.entities.UserInfoEntity

@Database(entities = [UserInfoEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getUserInfoDao(): UserInfoDao

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
