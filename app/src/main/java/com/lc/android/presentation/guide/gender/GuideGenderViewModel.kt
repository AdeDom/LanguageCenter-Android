package com.lc.android.presentation.guide.gender

import com.lc.android.base.BaseViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@FlowPreview
@ExperimentalCoroutinesApi
class GuideGenderViewModel : BaseViewModel<GuideGenderViewState>(GuideGenderViewState()) {

    private val channel = ConflatedBroadcastChannel<GuideGenderEvent>()

    fun process(action: GuideGenderEvent) {
        launch {
            channel.send(action)
        }
    }

    init {
        channel.asFlow()
            .onEach { event ->
                when (event) {
                    is GuideGenderEvent.Male -> setState { copy(gender = GuideGenderEvent.Male) }
                    is GuideGenderEvent.Female -> setState { copy(gender = GuideGenderEvent.Female) }
                }
            }
            .catch { setError(it) }
            .launchIn(this)
    }

}
