package com.lc.library.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lc.library.data.db.entities.VocabularyFeedbackEntity

@Dao
interface VocabularyFeedbackDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveVocabularyFeedback(entity: VocabularyFeedbackEntity)

    @Query("SELECT COUNT(*) FROM vocabulary_feedback WHERE vocabulary_id = :vocabularyId")
    suspend fun getDbVocabularyIsEvaluation(vocabularyId: String): Int

    @Query("SELECT * FROM vocabulary_feedback WHERE is_evaluation = 0 ORDER BY created DESC LIMIT 1")
    suspend fun getDbVocabularyFeedback(): VocabularyFeedbackEntity?

}
