package com.lc.library.presentation.usecase

import com.lc.library.data.db.entities.FriendInfoEntity
import com.lc.library.data.repository.Resource
import com.lc.server.models.response.BaseResponse

interface AddChatGroupFriendUseCase {
    suspend operator fun invoke(
        chatGroupId: Int?,
        friendId: String?,
        friendInfoEntity: FriendInfoEntity
    ): Resource<BaseResponse>
}
