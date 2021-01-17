package com.lc.android.presentation.addchatgroupdetail

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.lc.android.R
import com.lc.android.base.BaseFragment
import com.lc.library.data.db.entities.AddChatGroupDetailEntity
import kotlinx.android.synthetic.main.fragment_add_chat_group_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddChatGroupDetailFragment : BaseFragment(R.layout.fragment_add_chat_group_detail) {

    private val viewModel by viewModel<AddChatGroupDetailViewModel>()
    private val args by navArgs<AddChatGroupDetailFragmentArgs>()
    private val mAdapter by lazy { AddChatGroupDetailAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialView()
        observeViewModel()
        viewEvent()
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

            if (state.isClickable) {
                mAdapter.setListener { dialogAddFriend(args.chatGroupId, it.userId, it) }
            } else {
                mAdapter.setListener { }
            }
        }

        viewModel.getDbAddChatGroupDetail.observe(viewLifecycleOwner, { list ->
            if (list.isNullOrEmpty()) return@observe

            ivPlaceHolderDefault.visibility = View.GONE
            mAdapter.setList(list)
        })

        viewModel.error.observeError()
    }

    private fun viewEvent() {
        etSearch.addTextChangedListener {
            viewModel.setStateSearch(it.toString())
            viewModel.getDbAddChatGroupDetailBySearch()
        }
    }

    private fun dialogAddFriend(
        chatGroupId: Int,
        userId: String,
        addChatGroupDetailEntity: AddChatGroupDetailEntity,
    ) {
        AlertDialog.Builder(requireActivity()).apply {
            setTitle(R.string.dialog_add_chat_group_detail_title)
            setMessage(R.string.dialog_add_chat_group_detail_message)
            setPositiveButton(android.R.string.ok) { dialog, _ ->
                viewModel.callAddChatGroupDetail(chatGroupId, userId, addChatGroupDetailEntity)
                dialog.dismiss()
            }
            setNegativeButton(android.R.string.cancel) { dialog, _ ->
                dialog.dismiss()
            }
            show()
        }
    }

}
