package com.lc.android.presentation.addvocabulary

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.lc.android.R
import com.lc.android.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_add_vocabulary.*

class AddVocabularyFragment : BaseFragment(R.layout.fragment_add_vocabulary) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewEvent()
    }

    private fun viewEvent() {
        btCancel.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}
