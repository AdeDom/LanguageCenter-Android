package com.lc.library.db.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.lc.library.data.db.AppDatabase
import com.lc.library.data.db.dao.ChatListDao
import junit.framework.TestCase
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
    fun test() {

    }

}
