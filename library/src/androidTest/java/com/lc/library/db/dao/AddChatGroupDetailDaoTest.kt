package com.lc.library.db.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.lc.library.data.db.AppDatabase
import com.lc.library.data.db.dao.AddChatGroupDetailDao
import com.lc.library.data.db.entities.AddChatGroupDetailEntity
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AddChatGroupDetailDaoTest : TestCase() {

    private lateinit var db: AppDatabase
    private lateinit var dao: AddChatGroupDetailDao

    @Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        dao = db.getAddChatGroupDetailDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun saveAddChatGroupDetail_returnAllTable() = runBlocking {
        val entity1 = AddChatGroupDetailEntity(userId = "1", email = "one@gmail.com")
        val entity2 = AddChatGroupDetailEntity(userId = "2", email = "two@gmail.com")

        dao.saveAddChatGroupDetail(entity1)
        dao.saveAddChatGroupDetail(entity2)
        val result = dao.getDbAddChatGroupDetailList()

        assertThat(result).isEqualTo(listOf(entity1, entity2))
    }

    @Test
    fun getDbAddChatGroupDetailBySearch_returnIsEmpty() = runBlocking {
        val result = dao.getDbAddChatGroupDetailBySearch(null)

        assertThat(result).isEmpty()
    }

    @Test
    fun getDbAddChatGroupDetailBySearch_returnBySearch() = runBlocking {
        val entity1 = AddChatGroupDetailEntity(userId = "1", email = "one@gmail.com")
        val entity2 = AddChatGroupDetailEntity(userId = "2", email = "two@gmail.com")

        dao.saveAddChatGroupDetail(entity1)
        dao.saveAddChatGroupDetail(entity2)
        val result = dao.getDbAddChatGroupDetailBySearch("two@gmail.com")

        assertThat(result).isEqualTo(listOf(entity2))
    }

    @Test
    fun deleteAddChatGroupDetail_returnIsEmpty() = runBlocking {
        val entity1 = AddChatGroupDetailEntity(userId = "1", email = "one@gmail.com")
        val entity2 = AddChatGroupDetailEntity(userId = "2", email = "two@gmail.com")

        dao.saveAddChatGroupDetail(entity1)
        dao.saveAddChatGroupDetail(entity2)
        dao.deleteAddChatGroupDetail(null)
        val result = dao.getDbAddChatGroupDetailList()

        assertThat(result).isEqualTo(listOf(entity1, entity2))
    }

    @Test
    fun deleteAddChatGroupDetail_deleteEntityOne_returnListEntityTwoOnly() = runBlocking {
        val entity1 = AddChatGroupDetailEntity(userId = "1", email = "one@gmail.com")
        val entity2 = AddChatGroupDetailEntity(userId = "2", email = "two@gmail.com")

        dao.saveAddChatGroupDetail(entity1)
        dao.saveAddChatGroupDetail(entity2)
        dao.deleteAddChatGroupDetail("1")
        val result = dao.getDbAddChatGroupDetailList()

        assertThat(result).isEqualTo(listOf(entity2))
    }

    @Test
    fun deleteAllAddChatGroupDetail_returnIsEmpty() = runBlocking {
        val entity1 = AddChatGroupDetailEntity(userId = "1", email = "one@gmail.com")
        val entity2 = AddChatGroupDetailEntity(userId = "2", email = "two@gmail.com")

        dao.saveAddChatGroupDetail(entity1)
        dao.saveAddChatGroupDetail(entity2)
        dao.deleteAllAddChatGroupDetail()
        val result = dao.getDbAddChatGroupDetailList()

        assertThat(result).isEmpty()
    }

}
