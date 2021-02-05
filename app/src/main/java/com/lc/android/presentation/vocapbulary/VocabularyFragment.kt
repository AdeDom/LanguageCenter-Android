package com.lc.android.presentation.vocapbulary

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.lc.android.R
import com.lc.android.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_vocabulary.*

class VocabularyFragment : BaseFragment(R.layout.fragment_vocabulary) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewEvent()
    }

    private fun viewEvent() {
        fab.setOnClickListener {
            findNavController().navigate(R.id.action_vocabularyFragment_to_addVocabularyFragment)
        }
    }

}
