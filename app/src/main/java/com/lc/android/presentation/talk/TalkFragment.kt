package com.lc.android.presentation.talk

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.lc.android.R
import com.lc.android.base.BaseFragment
import com.lc.android.presentation.main.MainViewModel
import com.lc.android.util.hideSoftKeyboard
import com.lc.android.util.loadCircle
import com.lc.android.util.toast
import kotlinx.android.synthetic.main.fragment_talk.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class TalkFragment : BaseFragment(R.layout.fragment_talk) {

    private val viewModel by viewModel<TalkViewModel>()
    private val sharedViewModel by sharedViewModel<MainViewModel>()
    private val args by navArgs<TalkFragmentArgs>()
    private val mAdapter by lazy { TalkAdapter(args.userInfo.picture) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialView()
        observeViewModel()
        viewEvent()
        resultListener()
    }

    override fun onStart() {
        super.onStart()

        viewModel.callReadMessages(args.userInfo.userId)
    }

    private fun initialView() {
        val name = "${args.userInfo.givenName} ${args.userInfo.familyName}"
        tvName.text = name
        ivPicture.loadCircle(args.userInfo.picture)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context).apply {
                stackFromEnd = true
            }
            adapter = mAdapter
        }
    }

    private fun observeViewModel() {
        viewModel.attachFirstTime.observe {
            viewModel.saveChatList(args.userInfo)
        }

        viewModel.state.observe { state ->
            animationLoading.isVisible = state.isLoading
            ibSendMessage.isClickable = state.isSendMessage
            layoutTranslate.isVisible = state.isResultTranslate
        }

        viewModel.clearTextEvent.observe {
            etMessage.text?.clear()
        }

        viewModel.getDbTalkByOtherUserIdLiveData(args.userInfo.userId)
            .observe(viewLifecycleOwner, { talkEntity ->
                if (talkEntity == null) return@observe

                if (talkEntity.isNotEmpty()) {
                    mAdapter.submitList(talkEntity)
                    recyclerView.smoothScrollToPosition(talkEntity.lastIndex)
                }
            })

        viewModel.translateResultsEvent.observe {
            ibSendMessageTranslate.isVisible = it.translations.size == 1
            tvVocabularyText.text = it?.vocabulary

            if (it.translations.size == 1) {
                tvTranslationText.text = it.translations.singleOrNull()?.translation
            } else {
                var translateText = ""
                it?.translations?.forEachIndexed { index, translation ->
                    translateText += "[${index.plus(1)}] ${translation.translation}${if (index == it.translations.size - 1) "" else "\n"}"
                }
                tvTranslationText.text = translateText
            }
        }

        viewModel.talkWebSockets.observe {
            sharedViewModel.talkWebSockets()
        }

        viewModel.error.observeError()
    }

    private fun viewEvent() {
        ibTranslate.setOnClickListener {
            val navDirections = TalkFragmentDirections
                .actionTalkFragmentToGoogleTranslateDialog(viewModel.getIsTranslateThToEn())
            findNavController().navigate(navDirections)
        }

        ibClose.setOnClickListener {
            viewModel.setStateIsResultTranslateHide()
        }

        etMessage.addTextChangedListener { viewModel.setStateMessages(it.toString()) }

        ibSendMessage.setOnClickListener { viewModel.callSendMessage(args.userInfo.userId) }

        ibSendMessageTranslate.setOnClickListener { viewModel.callSendMessageTranslate(args.userInfo.userId) }

        requireView().setOnClickListener { activity?.hideSoftKeyboard() }

        ivPicture.setOnClickListener { navToUserInfo() }
        tvName.setOnClickListener { navToUserInfo() }

        mAdapter.setResendMessageListener {
            viewModel.setStateResendMessageTalkEntity(it)
            findNavController().navigate(R.id.action_talkFragment_to_talkMoreDialog)
        }

        layoutTranslate.setOnTouchListener { _, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_MOVE -> {
                    val layoutTranslateX = layoutTranslate.width / 2
                    val layoutTranslateY = layoutTranslate.height / 2

                    layoutTranslate.x = motionEvent.rawX - layoutTranslateX
                    layoutTranslate.y = motionEvent.rawY - layoutTranslateY
                }
            }
            true
        }

        etMessage.setOnLongClickListener {
            val text = etMessage.text.toString() + viewModel.getCopyTextMessage()
            etMessage.setText(text)
            etMessage.setSelection(text.length)
            context.toast("Paste")
            true
        }
    }

    private fun navToUserInfo() {
        val navDirections = TalkFragmentDirections
            .actionTalkFragmentToUserInfoFragment(null, true, args.userInfo)
        findNavController().navigate(navDirections)
    }

    private fun resultListener() {
        setFragmentResultListener(TalkMoreDialog.TALK_MORE) { _, bundle ->
            val talkMoreKey = bundle.getString(TalkMoreDialog.TALK_MORE_KEY)
            when (talkMoreKey) {
                TalkMoreDialog.RESEND_MESSAGE -> viewModel.callResendMessage()
            }
        }

        setFragmentResultListener(GoogleTranslateDialog.IS_TRANSLATE_TH_TO_EN_KEY) { _, bundle ->
            val isTranslateThToEn = bundle.getBoolean(GoogleTranslateDialog.IS_TRANSLATE_TH_TO_EN)
            viewModel.setIsTranslateThToEn(isTranslateThToEn)
        }

        setFragmentResultListener(GoogleTranslateDialog.TRANSLATE_TEXT_KEY) { _, bundle ->
            val translateText = bundle.getString(GoogleTranslateDialog.TRANSLATE_TEXT)
            if (!translateText.isNullOrBlank()) {
                viewModel.callLanguageCenterTranslate(translateText)
            }
        }
    }

}
