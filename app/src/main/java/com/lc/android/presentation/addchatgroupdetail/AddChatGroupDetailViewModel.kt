package com.lc.android.presentation.addchatgroupdetail

import androidx.lifecycle.LiveData
import com.lc.android.base.BaseViewModel
import com.lc.library.data.db.entities.AddChatGroupDetailEntity
import com.lc.library.data.repository.Resource
import com.lc.library.presentation.usecase.GetAddChatGroupDetailUseCase
import kotlinx.coroutines.launch

class AddChatGroupDetailViewModel(
    private val getAddChatGroupDetailUseCase: GetAddChatGroupDetailUseCase,
) : BaseViewModel<AddChatGroupDetailViewState>(AddChatGroupDetailViewState()) {

    val getDbAddChatGroupDetailLiveData: LiveData<AddChatGroupDetailEntity>
        get() = getAddChatGroupDetailUseCase.getDbAddChatGroupDetailLiveData()

    fun callFetchAddChatGroupDetail() {
        launch {
            setState { copy(isLoading = true) }

            when (val resource = getAddChatGroupDetailUseCase.callFetchAddChatGroupDetail()) {
                is Resource.Error -> setError(resource.throwable)
            }

            setState { copy(isLoading = false) }
        }
    }

}
