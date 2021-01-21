package com.lc.android.presentation.chats

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lc.android.R
import com.lc.android.util.loadCircle
import com.lc.library.data.db.entities.ChatListEntity
import kotlinx.android.synthetic.main.item_chats.view.*

class ChatsAdapter : RecyclerView.Adapter<ChatsAdapter.ChatsViewHolder>() {

    private var listener: ((ChatListEntity) -> Unit)? = null

    private val asyncListDiffer =
        AsyncListDiffer(this, object : DiffUtil.ItemCallback<ChatListEntity>() {
            override fun areItemsTheSame(
                oldItem: ChatListEntity,
                newItem: ChatListEntity
            ): Boolean {
                return oldItem.userId == newItem.userId
            }

            override fun areContentsTheSame(
                oldItem: ChatListEntity,
                newItem: ChatListEntity
            ): Boolean {
                return oldItem == newItem
            }
        })

    private val list: List<ChatListEntity>
        get() = asyncListDiffer.currentList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chats, parent, false)
        return ChatsViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatsViewHolder, position: Int) {
        holder.itemView.apply {
            val chatListEntity = list[position]

            val name = "${chatListEntity.givenName} ${chatListEntity.familyName}"
            tvName.text = name
            tvMessage.text = chatListEntity.messages
            tvDateTime.text = chatListEntity.dateTimeString
            ivPicture.loadCircle(chatListEntity.picture)

            setOnClickListener {
                listener?.invoke(chatListEntity)
            }
        }
    }

    override fun getItemCount(): Int = list.size

    fun submitList(list: List<ChatListEntity>) = asyncListDiffer.submitList(list)

    fun setListener(listener: (ChatListEntity) -> Unit) {
        this.listener = listener
    }

    inner class ChatsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}
