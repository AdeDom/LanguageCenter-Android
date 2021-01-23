package com.lc.android.presentation.chatgroupdetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lc.android.R
import com.lc.android.presentation.model.ChatGroup
import kotlinx.android.synthetic.main.item_change_chat_group.view.*

class ChangeChatGroupAdapter :
    RecyclerView.Adapter<ChangeChatGroupAdapter.ChangeChatGroupViewHolder>() {

    private val list: MutableList<ChatGroup>
        get() = asyncListDiffer.currentList
    private var listener: ((ChatGroup) -> Unit)? = null

    private val asyncListDiffer =
        AsyncListDiffer(this, object : DiffUtil.ItemCallback<ChatGroup>() {
            override fun areItemsTheSame(oldItem: ChatGroup, newItem: ChatGroup): Boolean {
                return oldItem.chatGroupId == newItem.chatGroupId
            }

            override fun areContentsTheSame(oldItem: ChatGroup, newItem: ChatGroup): Boolean {
                return oldItem == newItem
            }
        })

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChangeChatGroupViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_change_chat_group, parent, false)
        return ChangeChatGroupViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChangeChatGroupViewHolder, position: Int) {
        holder.itemView.apply {
            tvChatGroup.text = list[position].groupName

            tvChatGroup.setOnClickListener {
                listener?.invoke(list[position])
            }
        }
    }

    override fun getItemCount(): Int = list.size

    fun submitList(list: List<ChatGroup>) = asyncListDiffer.submitList(list)

    fun setListener(listener: ((ChatGroup) -> Unit)?) {
        this.listener = listener
    }

    class ChangeChatGroupViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}
