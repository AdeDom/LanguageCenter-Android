package com.lc.library.presentation.usecase

import androidx.lifecycle.LiveData
import com.lc.library.data.db.entities.AddChatGroupDetailEntity
import com.lc.library.data.repository.Resource
import com.lc.server.models.response.FetchAddChatGroupDetailResponse

interface GetAddChatGroupDetailUseCase {

    suspend fun callFetchAddChatGroupDetail(): Resource<FetchAddChatGroupDetailResponse>

    fun getDbAddChatGroupDetailLiveData(): LiveData<List<AddChatGroupDetailEntity>>

}
