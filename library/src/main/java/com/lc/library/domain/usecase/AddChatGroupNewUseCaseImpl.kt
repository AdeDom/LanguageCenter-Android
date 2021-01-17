package com.lc.library.domain.usecase

import com.lc.library.data.db.entities.FriendInfoEntity
import com.lc.library.data.repository.Resource
import com.lc.library.domain.repository.LanguageCenterRepository
import com.lc.library.presentation.usecase.AddChatGroupNewUseCase
import com.lc.server.models.request.AddChatGroupNewRequest
import com.lc.server.models.response.BaseResponse

class AddChatGroupNewUseCaseImpl(
    private val repository: LanguageCenterRepository,
) : AddChatGroupNewUseCase {
    override suspend fun invoke(
        addChatGroupNewRequest: AddChatGroupNewRequest,
        friendInfoEntity: FriendInfoEntity,
    ): Resource<BaseResponse> {
        return repository.callAddChatGroupNew(addChatGroupNewRequest, friendInfoEntity)
    }
}
