package com.lc.library.db.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.lc.library.data.db.AppDatabase
import com.lc.library.data.db.dao.FriendInfoDao
import com.lc.library.data.db.entities.FriendInfoEntity
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FriendInfoDaoTest : TestCase() {

    private lateinit var db: AppDatabase
    private lateinit var dao: FriendInfoDao

    @Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        dao = db.getFriendInfoDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun saveFriendInfo_saveTwoItem_returnTwoItemList() = runBlocking {
        val entity1 = FriendInfoEntity(userId = "1", email = "one@gmail.com")
        val entity2 = FriendInfoEntity(userId = "2", email = "two@gmail.com")
        dao.saveFriendInfo(entity1)
        dao.saveFriendInfo(entity2)

        val result = dao.getDbFriendInfoList()

        assertThat(result).isEqualTo(listOf(entity1, entity2))
    }

    @Test
    fun deleteFriendInfo() = runBlocking {
        val entity1 = FriendInfoEntity(userId = "1", email = "one@gmail.com")
        val entity2 = FriendInfoEntity(userId = "2", email = "two@gmail.com")
        dao.saveFriendInfo(entity1)
        dao.saveFriendInfo(entity2)

        dao.deleteFriendInfo()
        val result = dao.getDbFriendInfoList()

        assertThat(result).isEmpty()
    }

}
