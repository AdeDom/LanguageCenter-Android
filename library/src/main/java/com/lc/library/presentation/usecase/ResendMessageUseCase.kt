package com.lc.library.presentation.usecase

import com.lc.library.data.db.entities.TalkEntity
import com.lc.library.data.repository.Resource
import com.lc.server.models.request.ResendMessageRequest
import com.lc.server.models.response.BaseResponse

interface ResendMessageUseCase {

    suspend operator fun invoke(resendMessageRequest: ResendMessageRequest): Resource<BaseResponse>?

    suspend fun getDbTalkByTalkId(talkId: String): TalkEntity?

}
