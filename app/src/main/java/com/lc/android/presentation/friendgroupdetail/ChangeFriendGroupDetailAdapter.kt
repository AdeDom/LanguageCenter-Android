package com.lc.android.presentation.friendgroupdetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lc.android.R
import com.lc.android.presentation.model.ChatGroup
import kotlinx.android.synthetic.main.item_change_friend_group_detail.view.*

class ChangeFriendGroupDetailAdapter :
    RecyclerView.Adapter<ChangeFriendGroupDetailAdapter.ChangeFriendGroupDetailViewHolder>() {

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

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ChangeFriendGroupDetailViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_change_friend_group_detail, parent, false)
        return ChangeFriendGroupDetailViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChangeFriendGroupDetailViewHolder, position: Int) {
        holder.itemView.apply {
            val item = list[position]

            tvChatGroup.text = item.groupName

            tvChatGroup.setOnClickListener {
                listener?.invoke(item)
            }
        }
    }

    override fun getItemCount(): Int = list.size

    fun submitList(list: List<ChatGroup>) = asyncListDiffer.submitList(list)

    fun setListener(listener: ((ChatGroup) -> Unit)?) {
        this.listener = listener
    }

    class ChangeFriendGroupDetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}
