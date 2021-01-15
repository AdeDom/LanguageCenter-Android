package com.lc.android.presentation.addchatgroupdetail

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.lc.android.R
import com.lc.android.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_add_chat_group_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddChatGroupDetailFragment : BaseFragment(R.layout.fragment_add_chat_group_detail) {

    private val viewModel by viewModel<AddChatGroupDetailViewModel>()
    private val mAdapter by lazy { AddChatGroupDetailAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialView()
        observeViewModel()
    }

    private fun initialView() {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
        }
    }

    private fun observeViewModel() {
        viewModel.attachFirstTime.observe {
            viewModel.callFetchAddChatGroupDetail()
        }

        viewModel.state.observe { state ->
            animationLoading.isVisible = state.isLoading
        }

        viewModel.getDbAddChatGroupDetailLiveData.observe(viewLifecycleOwner, { list ->
            if (list.isNullOrEmpty()) return@observe

            ivPlaceHolderDefault.visibility = View.GONE
            mAdapter.setList(list)
        })

        viewModel.error.observeError()
    }

}
