package com.lc.android.presentation.chatgroupdetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lc.android.R
import com.lc.android.presentation.model.ChatGroup
import kotlinx.android.synthetic.main.item_change_chat_group.view.*

class ChangeChatGroupAdapter :
    RecyclerView.Adapter<ChangeChatGroupAdapter.ChangeChatGroupViewHolder>() {

    private var list = mutableListOf<ChatGroup>()
    private var listener: ((ChatGroup) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChangeChatGroupViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_change_chat_group, parent, false)
        return ChangeChatGroupViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChangeChatGroupViewHolder, position: Int) {
        holder.itemView.tvChatGroup.text = list[position].groupName

        holder.itemView.tvChatGroup.setOnClickListener {
            listener?.invoke(list[position])
        }
    }

    override fun getItemCount(): Int = list.size

    fun setList(list: List<ChatGroup>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun setListener(listener: ((ChatGroup) -> Unit)?) {
        this.listener = listener
    }

    class ChangeChatGroupViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}
