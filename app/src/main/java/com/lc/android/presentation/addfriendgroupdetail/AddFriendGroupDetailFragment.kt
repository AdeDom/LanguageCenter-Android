package com.lc.android.presentation.addfriendgroupdetail

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.lc.android.R
import com.lc.android.base.BaseFragment
import com.lc.android.presentation.model.UserInfoLocaleParcelable
import com.lc.android.presentation.model.UserInfoParcelable
import com.lc.library.data.db.entities.AddChatGroupDetailEntity
import kotlinx.android.synthetic.main.fragment_add_friend_group_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddFriendGroupDetailFragment : BaseFragment(R.layout.fragment_add_friend_group_detail) {

    private val viewModel by viewModel<AddFriendGroupDetailViewModel>()
    private val args by navArgs<AddFriendGroupDetailFragmentArgs>()
    private val mAdapter by lazy { AddFriendGroupDetailAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.callFetchAddChatGroupDetail()
    }

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
            viewModel.getDbAddChatGroupDetailBySearch()
        }

        viewModel.state.observe { state ->
            animationLoading.isVisible = state.isLoading

            if (state.isClickable) {
                mAdapter.setListener { navToUserInfo(it) }
            } else {
                mAdapter.setListener { }
            }
        }

        viewModel.getDbAddChatGroupDetail.observe(viewLifecycleOwner, { list ->
            if (list.isNullOrEmpty()) return@observe

            ivPlaceHolderDefault.visibility = View.GONE
            mAdapter.submitList(list)
        })

        viewModel.error.observeError()
    }

    private fun viewEvent() {
        etSearch.addTextChangedListener {
            viewModel.setStateSearch(it.toString())
            viewModel.getDbAddChatGroupDetailBySearch()
        }
    }

    private fun navToUserInfo(addChatGroupDetailEntity: AddChatGroupDetailEntity) {
        val userInfo = UserInfoParcelable(
            userId = addChatGroupDetailEntity.userId,
            email = addChatGroupDetailEntity.email,
            givenName = addChatGroupDetailEntity.givenName,
            familyName = addChatGroupDetailEntity.familyName,
            name = addChatGroupDetailEntity.name,
            picture = addChatGroupDetailEntity.picture,
            gender = addChatGroupDetailEntity.gender,
            age = addChatGroupDetailEntity.age,
            birthDateString = addChatGroupDetailEntity.birthDateString,
            birthDateLong = addChatGroupDetailEntity.birthDateLong,
            aboutMe = addChatGroupDetailEntity.aboutMe,
            localNatives = addChatGroupDetailEntity.localNatives.map {
                UserInfoLocaleParcelable(locale = it.locale, level = it.level)
            },
            localLearnings = addChatGroupDetailEntity.localLearnings.map {
                UserInfoLocaleParcelable(locale = it.locale, level = it.level)
            },
        )
        val navDirections = AddFriendGroupDetailFragmentDirections
            .actionAddFriendGroupDetailFragmentToUserInfoFragment(
                args.chatGroupId.toString(),
                false,
                userInfo,
            )
        findNavController().navigate(navDirections)
    }

}
