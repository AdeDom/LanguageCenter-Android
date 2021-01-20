package com.lc.library.domain.usecase

import androidx.lifecycle.LiveData
import com.lc.library.data.db.entities.TalkEntity
import com.lc.library.data.network.source.LanguageCenterDataSource
import com.lc.library.presentation.usecase.GetTalkUseCase

class GetTalkUseCaseImpl(
    private val dataSource: LanguageCenterDataSource,
) : GetTalkUseCase {

    override fun getDbTalkByOtherUserIdLiveData(otherUserId: String?): LiveData<List<TalkEntity>> {
        return dataSource.getDbTalkByOtherUserIdLiveData(otherUserId)
    }

}
