package com.lc.android.presentation.talk

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.lc.android.R
import com.lc.android.util.loadCircle
import com.lc.library.data.db.entities.TalkEntity

class TalkAdapter(
    private val picture: String?
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
        val layout = when (messagesType) {
            is MessagesType.ChatLeft -> R.layout.item_chat_left
            is MessagesType.ChatRight -> R.layout.item_chat_right
        }

        val view = LayoutInflater.from(parent.context)
            .inflate(layout, parent, false)
        return TalkViewHolder(view)
    }

    override fun onBindViewHolder(holder: TalkViewHolder, position: Int) {
        holder.apply {
            val talkEntity = list[position]

            // message
            tvMessage.text = talkEntity.messages

            // time
            tvTime.text = talkEntity.timeString

            // date
            if (position > 0 && talkEntity.dateString == list[position - 1].dateString) {
                tvDate.visibility = View.GONE
            } else {
                tvDate.text = talkEntity.dateString
            }

            // read
            if (talkEntity.isRead) tvRead.visibility = View.VISIBLE

            // picture
            ivPicture.loadCircle(picture)

            // is send message
            ivSendMessageCompleted.isVisible = talkEntity.isSendMessage
            animationSendMessage.isVisible = !talkEntity.isSendMessage
        }
    }

    override fun getItemCount(): Int = list.size

    override fun getItemViewType(position: Int): Int {
        messagesType = when (list[position].isSendType) {
            true -> MessagesType.ChatRight
            false -> MessagesType.ChatLeft
        }
        return super.getItemViewType(position)
    }

    fun submitList(list: List<TalkEntity>) = asyncListDiffer.submitList(list)

    inner class TalkViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvMessage: TextView = itemView.findViewById(R.id.tvMessage)
        val tvTime: TextView = itemView.findViewById(R.id.tvTime)
        val tvDate: TextView = itemView.findViewById(R.id.tvDate)
        val tvRead: TextView = itemView.findViewById(R.id.tvRead)
        val ivPicture: ImageView = itemView.findViewById(R.id.ivPicture)
        val ivSendMessageCompleted: ImageView = itemView.findViewById(R.id.ivSendMessageCompleted)
        val animationSendMessage: LottieAnimationView =
            itemView.findViewById(R.id.animationSendMessage)
    }

}
