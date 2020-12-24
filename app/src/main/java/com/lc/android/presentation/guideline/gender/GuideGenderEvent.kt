package com.lc.android.presentation.guideline.gender

sealed class GuideGenderEvent {
    object Male : GuideGenderEvent()
    object Female : GuideGenderEvent()
}
