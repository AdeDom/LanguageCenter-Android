package com.lc.library.db.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.lc.library.data.db.AppDatabase
import com.lc.library.data.db.dao.TalkDao
import com.lc.library.data.db.entities.TalkEntity
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TalkDaoTest : TestCase() {

    private lateinit var db: AppDatabase
    private lateinit var dao: TalkDao

    @Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        dao = db.getTalkDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun saveTalk_insertTwoItem_returnTwoItemList() = runBlocking {
        val entity1 = TalkEntity(
            talkId = "1",
            fromUserId = "123",
            toUserId = "321",
            messages = "hello",
            dateString = "10/4/2021",
            timeString = "14:59",
            dateTimeLong = 123456,
            isRead = false,
            isSendMessage = false,
            isSendType = false,
        )
        val entity2 = TalkEntity(
            talkId = "2",
            fromUserId = "321",
            toUserId = "123",
            messages = "world",
            dateString = "12/4/2021",
            timeString = "15:00",
            dateTimeLong = 654321,
            isRead = true,
            isSendMessage = true,
            isSendType = true,
        )
        dao.saveTalk(entity1)
        dao.saveTalk(entity2)

        val result = dao.getDbTalkList()

        assertThat(result).isEqualTo(listOf(entity1, entity2))
    }

    @Test
    fun getDbTalkByTalkId_getByTalkId_returnTalkEntity() = runBlocking {
        val entity1 = TalkEntity(
            talkId = "1",
            fromUserId = "123",
            toUserId = "321",
            messages = "hello",
            dateString = "10/4/2021",
            timeString = "14:59",
            dateTimeLong = 123456,
            isRead = false,
            isSendMessage = false,
            isSendType = false,
        )
        val entity2 = TalkEntity(
            talkId = "2",
            fromUserId = "321",
            toUserId = "123",
            messages = "world",
            dateString = "12/4/2021",
            timeString = "15:00",
            dateTimeLong = 654321,
            isRead = true,
            isSendMessage = true,
            isSendType = true,
        )
        dao.saveTalk(entity1)
        dao.saveTalk(entity2)

        val result = dao.getDbTalkByTalkId("2")

        assertThat(result).isEqualTo(entity2)
    }

    @Test
    fun getDbCountTalkByTalkId_searchOtherTalk_returnZero() = runBlocking {
        val entity1 = TalkEntity(
            talkId = "1",
            fromUserId = "123",
            toUserId = "321",
            messages = "hello",
            dateString = "10/4/2021",
            timeString = "14:59",
            dateTimeLong = 123456,
            isRead = false,
            isSendMessage = false,
            isSendType = false,
        )
        val entity2 = TalkEntity(
            talkId = "2",
            fromUserId = "321",
            toUserId = "123",
            messages = "world",
            dateString = "12/4/2021",
            timeString = "15:00",
            dateTimeLong = 654321,
            isRead = true,
            isSendMessage = true,
            isSendType = true,
        )
        dao.saveTalk(entity1)
        dao.saveTalk(entity2)

        val result = dao.getDbCountTalkByTalkId("3")

        assertThat(result).isEqualTo(0)
    }

    @Test
    fun getDbCountTalkByTalkId_searchByTalkId_returnOne() = runBlocking {
        val entity1 = TalkEntity(
            talkId = "1",
            fromUserId = "123",
            toUserId = "321",
            messages = "hello",
            dateString = "10/4/2021",
            timeString = "14:59",
            dateTimeLong = 123456,
            isRead = false,
            isSendMessage = false,
            isSendType = false,
        )
        val entity2 = TalkEntity(
            talkId = "2",
            fromUserId = "321",
            toUserId = "123",
            messages = "world",
            dateString = "12/4/2021",
            timeString = "15:00",
            dateTimeLong = 654321,
            isRead = true,
            isSendMessage = true,
            isSendType = true,
        )
        dao.saveTalk(entity1)
        dao.saveTalk(entity2)

        val result = dao.getDbCountTalkByTalkId("2")

        assertThat(result).isEqualTo(1)
    }

    @Test
    fun updateTalkSendMessage() = runBlocking {
        val entity1 = TalkEntity(
            talkId = "1",
            fromUserId = "123",
            toUserId = "321",
            messages = "hello",
            dateString = "10/4/2021",
            timeString = "14:59",
            dateTimeLong = 123456,
            isRead = false,
            isSendMessage = false,
            isSendType = false,
        )
        val entity2 = TalkEntity(
            talkId = "2",
            fromUserId = "321",
            toUserId = "123",
            messages = "world",
            dateString = "12/4/2021",
            timeString = "15:00",
            dateTimeLong = 654321,
            isRead = true,
            isSendMessage = true,
            isSendType = true,
        )
        val entity3 = TalkEntity(
            talkId = "1",
            fromUserId = "123",
            toUserId = "321",
            messages = "hello",
            dateString = "11/4/2021",
            timeString = "15:01",
            dateTimeLong = 113366,
            isRead = false,
            isSendMessage = true,
            isSendType = false,
        )
        dao.saveTalk(entity1)
        dao.saveTalk(entity2)

        dao.updateTalkSendMessage(
            talkId = "1",
            dateString = "11/4/2021",
            timeString = "15:01",
            dateTimeLong = 113366,
            isSendMessage = true,
        )
        val result = dao.getDbTalkByTalkId("1")

        assertThat(result).isEqualTo(entity3)
    }

    @Test
    fun deleteTalk() = runBlocking {
        val entity1 = TalkEntity(
            talkId = "1",
            fromUserId = "123",
            toUserId = "321",
            messages = "hello",
            dateString = "10/4/2021",
            timeString = "14:59",
            dateTimeLong = 123456,
            isRead = false,
            isSendMessage = false,
            isSendType = false,
        )
        val entity2 = TalkEntity(
            talkId = "2",
            fromUserId = "321",
            toUserId = "123",
            messages = "world",
            dateString = "12/4/2021",
            timeString = "15:00",
            dateTimeLong = 654321,
            isRead = true,
            isSendMessage = true,
            isSendType = true,
        )
        dao.saveTalk(entity1)
        dao.saveTalk(entity2)

        dao.deleteTalk()
        val result = dao.getDbTalkList()

        assertThat(result).isEmpty()
    }

}
