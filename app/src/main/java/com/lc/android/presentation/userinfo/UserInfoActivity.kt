package com.lc.android.presentation.userinfo

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.lc.android.R
import com.lc.android.base.BaseActivity
import com.lc.android.util.load
import com.lc.android.util.toast
import kotlinx.android.synthetic.main.activity_user_info.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserInfoActivity : BaseActivity() {

    private val viewModel by viewModel<UserInfoViewModel>()
    private val args by navArgs<UserInfoActivityArgs>()
    private val mLocalNativeAdapter by lazy { UserInfoLocaleAdapter() }
    private val mLocalLearningAdapter by lazy { UserInfoLocaleAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)

        initialView()
        observeViewModel()
        viewEvent()
    }

    private fun initialView() {
        recyclerViewLocalNatives.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mLocalNativeAdapter
        }

        recyclerViewLocalLearnings.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mLocalLearningAdapter
        }

        val (_, email, name, picture, gender, age, aboutMe, _, localNatives, localLearnings) = args.userInfoCommunity
        if (email.isNullOrBlank()) tvEmail.visibility = View.GONE
        if (name.isNullOrBlank()) tvName.visibility = View.GONE
        if (gender.isNullOrBlank()) tvGender.visibility = View.GONE
        if (age == null) tvAge.visibility = View.GONE
        if (aboutMe.isNullOrBlank()) tvAboutMe.visibility = View.GONE
        if (picture.isNullOrBlank()) ivPicture.visibility = View.GONE
        if (localNatives.isNullOrEmpty()) {
            tvLocaleNative.visibility = View.GONE
            recyclerViewLocalNatives.visibility = View.GONE
        }
        if (localLearnings.isNullOrEmpty()) {
            tvLocaleLearning.visibility = View.GONE
            recyclerViewLocalLearnings.visibility = View.GONE
        }

        tvName.text = getString(R.string.str_name, name)
        tvEmail.text = getString(R.string.str_email, email)
        tvGender.text = getString(R.string.str_gender, gender)
        tvAge.text = getString(R.string.str_age, age)
        tvAboutMe.text = getString(R.string.str_about_me, aboutMe)
        ivPicture.load(picture)
        mLocalNativeAdapter.setList(localNatives)
        mLocalLearningAdapter.setList(localLearnings)
    }

    private fun observeViewModel() {
        viewModel.attachFirstTime.observe {
            viewModel.callAddAlgorithm(args.userInfoCommunity.algorithm)
        }

        viewModel.state.observe { state ->
            animationLoading.isVisible = state.isLoading
            ibAddFriend.isClickable = state.isClickable
        }

        viewModel.addChatGroupNewEvent.observe { response ->
            if (response.success) {
                toast(response.message)
                ibAddFriend.visibility = View.GONE
            } else {
                toast(response.message, Toast.LENGTH_LONG)
            }
        }

        viewModel.error.observeError()
    }

    private fun viewEvent() {
        ibAddFriend.setOnClickListener {
            viewModel.callAddChatGroupNew(args.userInfoCommunity.userId)
        }
    }

}
