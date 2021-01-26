package com.lc.library.domain.usecase

import com.lc.library.data.db.entities.TalkEntity
import com.lc.library.data.network.source.LanguageCenterDataSource
import com.lc.library.data.repository.Resource
import com.lc.library.domain.repository.LanguageCenterRepository
import com.lc.library.presentation.usecase.ResendMessageUseCase
import com.lc.server.models.request.ResendMessageRequest
import com.lc.server.models.response.BaseResponse

class ResendMessageUseCaseImpl(
    private val repository: LanguageCenterRepository,
    private val dataSource: LanguageCenterDataSource,
) : ResendMessageUseCase {

    override suspend fun invoke(resendMessageRequest: ResendMessageRequest): Resource<BaseResponse>? {
        return repository.callResendMessage(resendMessageRequest)
    }

    override suspend fun getDbTalkByTalkId(talkId: String): TalkEntity? {
        return dataSource.getDbTalkByTalkId(talkId)
    }

}
