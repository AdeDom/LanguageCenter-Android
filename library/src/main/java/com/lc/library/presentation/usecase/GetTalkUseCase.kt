package com.lc.library.presentation.usecase

import com.lc.library.data.db.entities.TalkEntity
import kotlinx.coroutines.flow.Flow

interface GetTalkUseCase {

    fun getDbTalkByOtherUserIdFlow(otherUserId: String?): Flow<List<TalkEntity>>

}
