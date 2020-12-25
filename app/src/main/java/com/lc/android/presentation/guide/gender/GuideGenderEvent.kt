package com.lc.android.presentation.guide.gender

sealed class GuideGenderEvent {
    object Male : GuideGenderEvent()
    object Female : GuideGenderEvent()
}
