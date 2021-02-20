package com.lc.library.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lc.server.models.model.Translation

@Entity(tableName = "vocabulary_feedback")
data class VocabularyFeedbackEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "vocabulary_id") val vocabularyId: String,
    @ColumnInfo(name = "user_id") val userId: String? = null,
    @ColumnInfo(name = "vocabulary") val vocabulary: String,
    @ColumnInfo(name = "source_language") val sourceLanguage: String,
    @ColumnInfo(name = "reference") val reference: String? = null,
    @ColumnInfo(name = "vocabulary_group_name") val vocabularyGroupName: String,
    @ColumnInfo(name = "translations") val translations: List<Translation> = emptyList(),
    @ColumnInfo(name = "is_evaluation") val isEvaluation: Boolean,
    @ColumnInfo(name = "created") val created: Long,
)
