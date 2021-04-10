package com.lc.library.db.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.lc.library.data.db.AppDatabase
import com.lc.library.data.db.dao.UserInfoDao
import com.lc.library.data.db.entities.UserInfoEntity
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserInfoDaoTest : TestCase() {

    private lateinit var db: AppDatabase
    private lateinit var dao: UserInfoDao

    @Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        dao = db.getUserInfoDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun saveUserInfo() = runBlocking {
        val entity1 = UserInfoEntity(userId = "1", email = "one@gmail.com")
        dao.saveUserInfo(entity1)

        val result = dao.getDbUserInfo()

        assertThat(result).isEqualTo(entity1)
    }

    @Test
    fun deleteUserInfo() = runBlocking {
        val entity1 = UserInfoEntity(userId = "1", email = "one@gmail.com")
        dao.saveUserInfo(entity1)

        dao.deleteUserInfo()
        val result = dao.getDbUserInfo()

        assertThat(result).isNull()
    }

}
