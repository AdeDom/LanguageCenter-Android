package com.lc.library.presentation.usecase

import androidx.lifecycle.LiveData
import com.lc.library.data.db.entities.TalkEntity

interface GetTalkUseCase {

    fun getDbTalkByOtherUserIdLiveData(otherUserId: String?): LiveData<List<TalkEntity>>

}
