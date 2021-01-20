package com.lc.android.presentation.talk

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lc.android.R
import com.lc.library.data.db.entities.TalkEntity
import kotlinx.android.synthetic.main.item_chat_left.view.*

class TalkAdapter(
    private val userId: String?
) : RecyclerView.Adapter<TalkAdapter.TalkViewHolder>() {

    private lateinit var messagesType: MessagesType

    private val asyncListDiffer =
        AsyncListDiffer(this, object : DiffUtil.ItemCallback<TalkEntity>() {
            override fun areItemsTheSame(oldItem: TalkEntity, newItem: TalkEntity): Boolean {
                return oldItem.talkId == newItem.talkId
            }

            override fun areContentsTheSame(oldItem: TalkEntity, newItem: TalkEntity): Boolean {
                return oldItem == newItem
            }
        })

    private val list: List<TalkEntity>
        get() = asyncListDiffer.currentList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TalkViewHolder {
        val layout = if (messagesType == MessagesType.ChatLeft) {
            R.layout.item_chat_left
        } else {
            R.layout.item_chat_right
        }

        val view = LayoutInflater.from(parent.context)
            .inflate(layout, parent, false)
        return TalkViewHolder(view)
    }

    override fun onBindViewHolder(holder: TalkViewHolder, position: Int) {
        holder.itemView.apply {
            val talkEntity = list[position]
            tvMessage.text = talkEntity.messages
        }
    }

    override fun getItemCount(): Int = list.size

    override fun getItemViewType(position: Int): Int {
        messagesType = if (list[position].toUserId == userId) {
            MessagesType.ChatRight
        } else {
            MessagesType.ChatLeft
        }
        return super.getItemViewType(position)
    }

    fun submitList(list: List<TalkEntity>) = asyncListDiffer.submitList(list)

    inner class TalkViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}
