package com.lc.library.presentation.usecase

import androidx.lifecycle.LiveData
import com.lc.library.data.db.entities.FriendInfoEntity

interface FetchFriendInfoUseCase {

    fun getDbFriendInfoLiveData(): LiveData<List<FriendInfoEntity>>

}
