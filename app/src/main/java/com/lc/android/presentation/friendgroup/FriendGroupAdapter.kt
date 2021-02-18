package com.lc.android.presentation.friendgroup

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lc.android.R
import com.lc.server.models.model.ChatGroup
import kotlinx.android.synthetic.main.item_friend_group.view.*

class FriendGroupAdapter : RecyclerView.Adapter<FriendGroupAdapter.FriendGroupViewHolder>() {

    private val list: MutableList<ChatGroup>
        get() = asyncListDiffer.currentList
    private var listener: ((Int?, MutableList<ChatGroup>) -> Unit)? = null
    private var moreListener: ((ChatGroup?) -> Unit)? = null

    private val asyncListDiffer =
        AsyncListDiffer(this, object : DiffUtil.ItemCallback<ChatGroup>() {
            override fun areItemsTheSame(oldItem: ChatGroup, newItem: ChatGroup): Boolean {
                return oldItem.chatGroupId == newItem.chatGroupId
            }

            override fun areContentsTheSame(oldItem: ChatGroup, newItem: ChatGroup): Boolean {
                return oldItem == newItem
            }
        })

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendGroupViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_friend_group, parent, false)
        return FriendGroupViewHolder(view)
    }

    override fun onBindViewHolder(holder: FriendGroupViewHolder, position: Int) {
        holder.itemView.apply {
            val item = list[position]

            tvChatGroupName.text = item.groupName

            setOnClickListener {
                listener?.invoke(item.chatGroupId, list)
            }

            ibMore.setOnClickListener {
                moreListener?.invoke(item)
            }
        }
    }

    override fun getItemCount(): Int = list.size

    fun submitList(list: List<ChatGroup>) = asyncListDiffer.submitList(list)

    fun setListener(listener: ((Int?, MutableList<ChatGroup>) -> Unit)?) {
        this.listener = listener
    }

    fun setMoreListener(moreListener: ((ChatGroup?) -> Unit)?) {
        this.moreListener = moreListener
    }

    class FriendGroupViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}
