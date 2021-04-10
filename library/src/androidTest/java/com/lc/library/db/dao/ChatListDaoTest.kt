package com.lc.library.db.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.lc.library.data.db.AppDatabase
import com.lc.library.data.db.dao.ChatListDao
import com.lc.library.data.db.entities.ChatListEntity
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ChatListDaoTest : TestCase() {

    private lateinit var db: AppDatabase
    private lateinit var dao: ChatListDao

    @Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        dao = db.getChatListDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun saveChatListEntity_saveTwoEntity_returnListTwoItem() = runBlocking {
        val entity1 = ChatListEntity(
            userId = "1",
            email = "one@gmail.com",
            messages = "hello",
            dateTimeString = "10/4/2021",
            dateTimeLong = 123456
        )
        val entity2 = ChatListEntity(
            userId = "2",
            email = "two@gmail.com",
            messages = "world",
            dateTimeString = "11/4/2021",
            dateTimeLong = 234567
        )
        dao.saveChatListEntity(entity1)
        dao.saveChatListEntity(entity2)

        val result = dao.getDbChatListAll()

        assertThat(result).isEqualTo(listOf(entity1, entity2))
    }

    @Test
    fun getDbChatListBySearch_searchNull_returnIsEmpty() = runBlocking {
        val entity1 = ChatListEntity(
            userId = "1",
            email = "one@gmail.com",
            givenName = "oneone",
            messages = "hello",
            dateTimeString = "10/4/2021",
            dateTimeLong = 123456
        )
        val entity2 = ChatListEntity(
            userId = "2",
            email = "two@gmail.com",
            givenName = "twotwo",
            messages = "world",
            dateTimeString = "11/4/2021",
            dateTimeLong = 234567
        )
        dao.saveChatListEntity(entity1)
        dao.saveChatListEntity(entity2)

        val result = dao.getDbChatListBySearch(null)

        assertThat(result).isEmpty()
    }

    @Test
    fun getDbChatListBySearch_searchEntityTwo_returnEntityTwoOnly() = runBlocking {
        val entity1 = ChatListEntity(
            userId = "1",
            email = "one@gmail.com",
            givenName = "oneone",
            messages = "hello",
            dateTimeString = "10/4/2021",
            dateTimeLong = 123456
        )
        val entity2 = ChatListEntity(
            userId = "2",
            email = "two@gmail.com",
            givenName = "twotwo",
            messages = "world",
            dateTimeString = "11/4/2021",
            dateTimeLong = 234567
        )
        dao.saveChatListEntity(entity1)
        dao.saveChatListEntity(entity2)

        val result = dao.getDbChatListBySearch("twotwo")

        assertThat(result).isEqualTo(listOf(entity2))
    }

    @Test
    fun getDbChatListCountByUserId_searchOtherUser_returnZero() = runBlocking {
        val entity1 = ChatListEntity(
            userId = "1",
            email = "one@gmail.com",
            givenName = "oneone",
            messages = "hello",
            dateTimeString = "10/4/2021",
            dateTimeLong = 123456
        )
        val entity2 = ChatListEntity(
            userId = "2",
            email = "two@gmail.com",
            givenName = "twotwo",
            messages = "world",
            dateTimeString = "11/4/2021",
            dateTimeLong = 234567
        )
        dao.saveChatListEntity(entity1)
        dao.saveChatListEntity(entity2)

        val result = dao.getDbChatListCountByUserId("3")

        assertThat(result).isEqualTo(0)
    }

    @Test
    fun getDbChatListCountByUserId_searchUserInDb_returnOne() = runBlocking {
        val entity1 = ChatListEntity(
            userId = "1",
            email = "one@gmail.com",
            givenName = "oneone",
            messages = "hello",
            dateTimeString = "10/4/2021",
            dateTimeLong = 123456
        )
        val entity2 = ChatListEntity(
            userId = "2",
            email = "two@gmail.com",
            givenName = "twotwo",
            messages = "world",
            dateTimeString = "11/4/2021",
            dateTimeLong = 234567
        )
        dao.saveChatListEntity(entity1)
        dao.saveChatListEntity(entity2)

        val result = dao.getDbChatListCountByUserId("2")

        assertThat(result).isEqualTo(1)
    }

    @Test
    fun updateChatListNewMessage() = runBlocking {
        val entity1 = ChatListEntity(
            userId = "1",
            email = "one@gmail.com",
            givenName = "oneone",
            messages = "hello",
            dateTimeString = "10/4/2021",
            dateTimeLong = 123456
        )
        val entity2 = ChatListEntity(
            userId = "2",
            email = "two@gmail.com",
            givenName = "twotwo",
            messages = "world",
            dateTimeString = "11/4/2021",
            dateTimeLong = 234567
        )
        val entity3 = ChatListEntity(
            userId = "2",
            email = "two@gmail.com",
            givenName = "twotwo",
            messages = "hello world",
            dateTimeString = "12/4/2021",
            dateTimeLong = 224466
        )
        dao.saveChatListEntity(entity1)
        dao.saveChatListEntity(entity2)

        dao.updateChatListNewMessage(
            userId = "2",
            messages = "hello world",
            dateTimeString = "12/4/2021",
            dateTimeLong = 224466
        )
        val result = dao.getDbChatListAll()

        assertThat(result).isEqualTo(listOf(entity1, entity3))
    }

    @Test
    fun deleteChatList() = runBlocking {
        val entity1 = ChatListEntity(
            userId = "1",
            email = "one@gmail.com",
            givenName = "oneone",
            messages = "hello",
            dateTimeString = "10/4/2021",
            dateTimeLong = 123456
        )
        val entity2 = ChatListEntity(
            userId = "2",
            email = "two@gmail.com",
            givenName = "twotwo",
            messages = "world",
            dateTimeString = "11/4/2021",
            dateTimeLong = 234567
        )
        dao.saveChatListEntity(entity1)
        dao.saveChatListEntity(entity2)

        dao.deleteChatList()
        val result = dao.getDbChatListAll()

        assertThat(result).isEmpty()
    }

}
