package com.lc.library.domain.usecase

import com.lc.library.data.db.entities.FriendInfoEntity
import com.lc.library.data.network.source.LanguageCenterDataSource
import com.lc.library.presentation.usecase.FetchFriendInfoUseCase
import kotlinx.coroutines.flow.Flow

class FetchFriendInfoUseCaseImpl(
    private val dataSource: LanguageCenterDataSource,
) : FetchFriendInfoUseCase {

    override fun getDbFriendInfoFlow(): Flow<List<FriendInfoEntity>> {
        return dataSource.getDbFriendInfoFlow()
    }

}
