package com.lc.library.presentation.usecase

import com.lc.library.data.db.entities.FriendInfoEntity
import kotlinx.coroutines.flow.Flow

interface FetchFriendInfoUseCase {

    fun getDbFriendInfoFlow(): Flow<List<FriendInfoEntity>>

}
