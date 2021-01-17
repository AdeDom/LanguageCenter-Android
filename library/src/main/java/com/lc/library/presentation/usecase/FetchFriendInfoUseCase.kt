package com.lc.library.presentation.usecase

import androidx.lifecycle.LiveData
import com.lc.library.data.db.entities.FriendInfoEntity
import com.lc.library.data.repository.Resource
import com.lc.server.models.request.FetchFriendInfoResponse

interface FetchFriendInfoUseCase {

    suspend fun callFetchFriendInfo(): Resource<FetchFriendInfoResponse>

    fun getDbFriendInfoLiveData(): LiveData<List<FriendInfoEntity>>

}
