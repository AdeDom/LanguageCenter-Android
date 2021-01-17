package com.lc.library.domain.usecase

import androidx.lifecycle.LiveData
import com.lc.library.data.db.entities.FriendInfoEntity
import com.lc.library.data.network.source.LanguageCenterDataSource
import com.lc.library.data.repository.Resource
import com.lc.library.domain.repository.LanguageCenterRepository
import com.lc.library.presentation.usecase.FetchFriendInfoUseCase
import com.lc.server.models.request.FetchFriendInfoResponse

class FetchFriendInfoUseCaseImpl(
    private val repository: LanguageCenterRepository,
    private val dataSource: LanguageCenterDataSource,
) : FetchFriendInfoUseCase {

    override suspend fun callFetchFriendInfo(): Resource<FetchFriendInfoResponse> {
        return repository.callFetchFriendInfo()
    }

    override fun getDbFriendInfoLiveData(): LiveData<List<FriendInfoEntity>> {
        return dataSource.getDbFriendInfoLiveData()
    }

}
