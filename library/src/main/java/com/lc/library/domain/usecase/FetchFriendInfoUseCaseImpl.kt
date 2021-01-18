package com.lc.library.domain.usecase

import androidx.lifecycle.LiveData
import com.lc.library.data.db.entities.FriendInfoEntity
import com.lc.library.data.network.source.LanguageCenterDataSource
import com.lc.library.presentation.usecase.FetchFriendInfoUseCase

class FetchFriendInfoUseCaseImpl(
    private val dataSource: LanguageCenterDataSource,
) : FetchFriendInfoUseCase {

    override fun getDbFriendInfoLiveData(): LiveData<List<FriendInfoEntity>> {
        return dataSource.getDbFriendInfoLiveData()
    }

}
