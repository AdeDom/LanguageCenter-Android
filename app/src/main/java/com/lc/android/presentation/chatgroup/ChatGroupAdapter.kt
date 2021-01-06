package com.lc.android.presentation.chatgroup

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lc.android.R
import com.lc.server.models.model.ChatGroup
import kotlinx.android.synthetic.main.item_chat_group.view.*

class ChatGroupAdapter : RecyclerView.Adapter<ChatGroupAdapter.ChatGroupViewHolder>() {

    private val list by lazy { mutableListOf<ChatGroup>() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatGroupViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_chat_group, parent, false)
        return ChatGroupViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatGroupViewHolder, position: Int) {
        holder.itemView.tvChatGroupName.text = list[position].groupName
    }

    override fun getItemCount(): Int = list.size

    fun setList(list: List<ChatGroup>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    class ChatGroupViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}
