package com.lc.android.presentation.vocabularydetail

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.lc.android.R
import com.lc.android.base.BaseFragment
import com.lc.android.util.toast

class VocabularyDetailFragment : BaseFragment(R.layout.fragment_vocabulary_detail) {

    private val args by navArgs<VocabularyDetailFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        context.toast(args.vocabularyGroupId.toString())
    }

}
