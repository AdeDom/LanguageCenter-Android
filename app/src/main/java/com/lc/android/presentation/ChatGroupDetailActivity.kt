package com.lc.android.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.fragment.NavHostFragment
import com.lc.android.R
import com.lc.android.presentation.chatgroup.ChatGroupFragment
import kotlinx.android.synthetic.main.activity_chat_group_detail.*

class ChatGroupDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_group_detail)

        val chatGroupId = intent.getIntExtra(ChatGroupFragment.CHAT_GROUP_ID, 0)

        (fragment as NavHostFragment)
            .navController
            .setGraph(
                R.navigation.nav_chat_group_detail,
                bundleOf(CHAT_GROUP_ID to chatGroupId)
            )
    }

    companion object {
        const val CHAT_GROUP_ID = "chatGroupId"
    }

}
