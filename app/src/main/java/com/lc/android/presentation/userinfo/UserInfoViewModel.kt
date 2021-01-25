package com.lc.android.presentation.userinfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lc.android.base.BaseViewModel
import com.lc.android.presentation.model.UserInfoParcelable
import com.lc.library.data.db.entities.FriendInfoEntity
import com.lc.library.data.repository.Resource
import com.lc.library.presentation.usecase.AddAlgorithmUseCase
import com.lc.library.presentation.usecase.AddChatGroupFriendUseCase
import com.lc.library.presentation.usecase.FetchFriendInfoUseCase
import com.lc.server.models.model.UserInfoLocale
import com.lc.server.models.request.AddAlgorithmRequest
import com.lc.server.models.request.AddChatGroupFriendRequest
import com.lc.server.models.response.BaseResponse
import kotlinx.coroutines.launch

class UserInfoViewModel(
    private val addAlgorithmUseCase: AddAlgorithmUseCase,
    private val addChatGroupFriendUseCase: AddChatGroupFriendUseCase,
    private val fetchFriendInfoUseCase: FetchFriendInfoUseCase,
) : BaseViewModel<UserInfoViewState>(UserInfoViewState()) {

    private val _addChatGroupNewEvent = MutableLiveData<BaseResponse>()
    val addChatGroupNewEvent: LiveData<BaseResponse>
        get() = _addChatGroupNewEvent

    val getDbFriendInfoLiveData: LiveData<List<FriendInfoEntity>>
        get() = fetchFriendInfoUseCase.getDbFriendInfoLiveData()

    fun callAddAlgorithm(algorithm: String?) {
        launch {
            setState { copy(isLoading = true, isClickable = false) }

            val request = AddAlgorithmRequest(algorithm)
            when (val resource = addAlgorithmUseCase(request)) {
                is Resource.Error -> setError(resource.throwable)
            }

            setState { copy(isLoading = false, isClickable = true) }
        }
    }

    fun callAddChatGroupNew(chatGroupId: Int?, friendInfo: UserInfoParcelable) {
        if (friendInfo.userId.isNullOrBlank()) return

        launch {
            setState { copy(isLoading = true, isClickable = false) }

            val entity = FriendInfoEntity(
                userId = friendInfo.userId,
                email = friendInfo.email,
                givenName = friendInfo.givenName,
                familyName = friendInfo.familyName,
                name = friendInfo.name,
                picture = friendInfo.picture,
                gender = friendInfo.gender,
                age = friendInfo.age,
                birthDateString = friendInfo.birthDateString,
                birthDateLong = friendInfo.birthDateLong,
                aboutMe = friendInfo.aboutMe,
                localNatives = friendInfo.localNatives?.map {
                    UserInfoLocale(locale = it.locale, level = it.level)
                } ?: emptyList(),
                localLearnings = friendInfo.localLearnings?.map {
                    UserInfoLocale(locale = it.locale, level = it.level)
                } ?: emptyList(),
            )

            val request = AddChatGroupFriendRequest(
                chatGroupId = chatGroupId,
                friendUserId = friendInfo.userId,
            )
            when (val resource = addChatGroupFriendUseCase(request, entity)) {
                is Resource.Success -> _addChatGroupNewEvent.value = resource.data
                is Resource.Error -> setError(resource.throwable)
            }

            setState { copy(isLoading = false, isClickable = true) }
        }
    }

}
