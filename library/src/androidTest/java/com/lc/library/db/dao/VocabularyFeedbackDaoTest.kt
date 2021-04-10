package com.lc.library.db.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.lc.library.data.db.AppDatabase
import com.lc.library.data.db.dao.VocabularyFeedbackDao
import com.lc.library.data.db.entities.VocabularyFeedbackEntity
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class VocabularyFeedbackDaoTest : TestCase() {

    private lateinit var db: AppDatabase
    private lateinit var dao: VocabularyFeedbackDao

    @Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        dao = db.getVocabularyFeedbackDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun saveVocabularyFeedback() = runBlocking {
        val entity1 = VocabularyFeedbackEntity(
            vocabularyId = "1",
            userId = "1",
            vocabulary = "hello",
            sourceLanguage = "en",
            reference = "lc",
            vocabularyGroupName = "1",
            isEvaluation = false,
            created = 123,
        )
        val entity2 = VocabularyFeedbackEntity(
            vocabularyId = "2",
            userId = "2",
            vocabulary = "world",
            sourceLanguage = "en",
            reference = "lc",
            vocabularyGroupName = "1",
            isEvaluation = true,
            created = 456,
        )
        dao.saveVocabularyFeedback(entity1)
        dao.saveVocabularyFeedback(entity2)

        val result = dao.getDbVocabularyFeedbackList()

        assertThat(result).isEqualTo(listOf(entity1, entity2))
    }

    @Test
    fun getDbVocabularyIsEvaluation_noData_returnZero() = runBlocking {
        val entity1 = VocabularyFeedbackEntity(
            vocabularyId = "1",
            userId = "1",
            vocabulary = "hello",
            sourceLanguage = "en",
            reference = "lc",
            vocabularyGroupName = "1",
            isEvaluation = false,
            created = 123,
        )
        val entity2 = VocabularyFeedbackEntity(
            vocabularyId = "2",
            userId = "2",
            vocabulary = "world",
            sourceLanguage = "en",
            reference = "lc",
            vocabularyGroupName = "1",
            isEvaluation = true,
            created = 456,
        )
        dao.saveVocabularyFeedback(entity1)
        dao.saveVocabularyFeedback(entity2)

        val result = dao.getDbVocabularyIsEvaluation("3")

        assertThat(result).isEqualTo(0)
    }

    @Test
    fun getDbVocabularyIsEvaluation_haveData_returnOne() = runBlocking {
        val entity1 = VocabularyFeedbackEntity(
            vocabularyId = "1",
            userId = "1",
            vocabulary = "hello",
            sourceLanguage = "en",
            reference = "lc",
            vocabularyGroupName = "1",
            isEvaluation = false,
            created = 123,
        )
        val entity2 = VocabularyFeedbackEntity(
            vocabularyId = "2",
            userId = "2",
            vocabulary = "world",
            sourceLanguage = "en",
            reference = "lc",
            vocabularyGroupName = "1",
            isEvaluation = true,
            created = 456,
        )
        dao.saveVocabularyFeedback(entity1)
        dao.saveVocabularyFeedback(entity2)

        val result = dao.getDbVocabularyIsEvaluation("2")

        assertThat(result).isEqualTo(1)
    }

    @Test
    fun getDbVocabularyFeedback() = runBlocking {
        val entity1 = VocabularyFeedbackEntity(
            vocabularyId = "1",
            userId = "1",
            vocabulary = "hello",
            sourceLanguage = "en",
            reference = "lc",
            vocabularyGroupName = "1",
            isEvaluation = false,
            created = 455,
        )
        val entity2 = VocabularyFeedbackEntity(
            vocabularyId = "2",
            userId = "2",
            vocabulary = "world",
            sourceLanguage = "en",
            reference = "lc",
            vocabularyGroupName = "1",
            isEvaluation = false,
            created = 456,
        )
        dao.saveVocabularyFeedback(entity1)
        dao.saveVocabularyFeedback(entity2)

        val result = dao.getDbVocabularyFeedback()

        assertThat(result).isEqualTo(entity2)
    }

    @Test
    fun updateVocabularyFeedbackIsEvaluation() = runBlocking {
        val entity1 = VocabularyFeedbackEntity(
            vocabularyId = "1",
            userId = "1",
            vocabulary = "hello",
            sourceLanguage = "en",
            reference = "lc",
            vocabularyGroupName = "1",
            isEvaluation = false,
            created = 123,
        )
        val entity2 = VocabularyFeedbackEntity(
            vocabularyId = "2",
            userId = "2",
            vocabulary = "world",
            sourceLanguage = "en",
            reference = "lc",
            vocabularyGroupName = "1",
            isEvaluation = false,
            created = 456,
        )
        val entity3 = VocabularyFeedbackEntity(
            vocabularyId = "2",
            userId = "2",
            vocabulary = "world",
            sourceLanguage = "en",
            reference = "lc",
            vocabularyGroupName = "1",
            isEvaluation = true,
            created = 456,
        )
        dao.saveVocabularyFeedback(entity1)
        dao.saveVocabularyFeedback(entity2)

        dao.updateVocabularyFeedbackIsEvaluation("2")
        val result = dao.getDbVocabularyFeedbackList().single { it.vocabularyId == "2" }

        assertThat(result).isEqualTo(entity3)
    }

}
