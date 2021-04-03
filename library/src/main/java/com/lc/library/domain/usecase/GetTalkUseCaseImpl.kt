package com.lc.library.domain.usecase

import com.lc.library.data.db.entities.TalkEntity
import com.lc.library.data.network.source.LanguageCenterDataSource
import com.lc.library.presentation.usecase.GetTalkUseCase
import kotlinx.coroutines.flow.Flow

class GetTalkUseCaseImpl(
    private val dataSource: LanguageCenterDataSource,
) : GetTalkUseCase {

    override fun getDbTalkByOtherUserIdFlow(otherUserId: String?): Flow<List<TalkEntity>> {
        return dataSource.getDbTalkByOtherUserIdFlow(otherUserId)
    }

}
