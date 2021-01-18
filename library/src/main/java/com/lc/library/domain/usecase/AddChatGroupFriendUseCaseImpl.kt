package com.lc.library.domain.usecase

import com.lc.library.data.db.entities.FriendInfoEntity
import com.lc.library.data.repository.Resource
import com.lc.library.domain.repository.LanguageCenterRepository
import com.lc.library.presentation.usecase.AddChatGroupFriendUseCase
import com.lc.server.models.request.AddChatGroupFriendRequest
import com.lc.server.models.response.BaseResponse

class AddChatGroupFriendUseCaseImpl(
    private val repository: LanguageCenterRepository,
) : AddChatGroupFriendUseCase {
    override suspend fun invoke(
        addChatGroupFriendRequest: AddChatGroupFriendRequest,
        friendInfoEntity: FriendInfoEntity
    ): Resource<BaseResponse> {
        return repository.callAddChatGroupFriend(addChatGroupFriendRequest, friendInfoEntity)
    }
}
