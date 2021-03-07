package com.lc.library.domain.usecase

import com.lc.library.data.repository.Resource
import com.lc.library.domain.repository.LanguageCenterRepository
import com.lc.library.presentation.usecase.FetchCommunityUseCase
import com.lc.server.models.model.Community
import com.lc.server.models.response.FetchCommunityResponse
import java.util.*

class FetchCommunityUseCaseImpl(
    private val repository: LanguageCenterRepository
) : FetchCommunityUseCase {
    override suspend fun invoke(): Resource<FetchCommunityResponse> {
        return repository.callFetchCommunity()
    }

    override fun searchCommunity(communities: List<Community>, search: String): List<Community> {
        val list = mutableListOf<Community>()

        val s = search.toLowerCase(Locale.getDefault())
        communities.forEach {
            val email = it.email?.toLowerCase(Locale.getDefault())
            val givenName = it.givenName?.toLowerCase(Locale.getDefault())
            val familyName = it.familyName?.toLowerCase(Locale.getDefault())
            val aboutMe = it.aboutMe?.toLowerCase(Locale.getDefault())
            if ((email?.contains(s) == true) ||
                (givenName?.contains(s) == true) ||
                (familyName?.contains(s) == true) ||
                (aboutMe?.contains(s) == true)
            ) {
                list.add(it)
            }
        }

        return list
    }
}
