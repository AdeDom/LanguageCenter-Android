package com.lc.android.presentation.chatgroupdetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lc.android.R
import com.lc.android.util.loadCircle
import com.lc.server.models.model.ChatGroupDetail
import com.lc.server.util.LanguageCenterConstant
import kotlinx.android.synthetic.main.item_chat_group_detail.view.*

class ChatGroupDetailAdapter :
    RecyclerView.Adapter<ChatGroupDetailAdapter.ChatGroupDetailViewHolder>() {

    private val list: MutableList<ChatGroupDetail>
        get() = asyncListDiffer.currentList
    private var listener: ((ChatGroupDetail) -> Unit)? = null
    private var moreListener: ((ChatGroupDetail) -> Unit)? = null

    private val asyncListDiffer =
        AsyncListDiffer(this, object : DiffUtil.ItemCallback<ChatGroupDetail>() {
            override fun areItemsTheSame(
                oldItem: ChatGroupDetail,
                newItem: ChatGroupDetail
            ): Boolean {
                return oldItem.userId == newItem.userId
            }

            override fun areContentsTheSame(
                oldItem: ChatGroupDetail,
                newItem: ChatGroupDetail
            ): Boolean {
                return oldItem == newItem
            }
        })

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatGroupDetailViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_chat_group_detail, parent, false)
        return ChatGroupDetailViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatGroupDetailViewHolder, position: Int) {
        val item = list[position]

        holder.itemView.apply {
            // set visibility
            if (item.email.isNullOrBlank()) tvEmail.visibility = View.GONE
            if (item.gender.isNullOrBlank()) ivGender.visibility = View.GONE
            if (item.age == null) tvAge.visibility = View.GONE
            if (item.givenName.isNullOrBlank() && item.familyName.isNullOrBlank()) {
                tvName.visibility = View.GONE
            }
            if (item.localNatives.isNullOrEmpty()) {
                layoutNative.visibility = View.GONE
            }
            if (item.localLearnings.isNullOrEmpty()) {
                layoutLearning.visibility = View.GONE
            }
            item.localNatives.filter { it.locale == LanguageCenterConstant.LOCALE_THAI }.forEach {
                tvLocaleNativeTh.visibility = View.VISIBLE
            }
            item.localNatives.filter { it.locale == LanguageCenterConstant.LOCALE_ENGLISH }
                .forEach {
                    tvLocaleNativeEn.visibility = View.VISIBLE
                }
            item.localLearnings.filter { it.locale == LanguageCenterConstant.LOCALE_THAI }.forEach {
                tvLocaleLearningTh.visibility = View.VISIBLE
            }
            item.localLearnings.filter { it.locale == LanguageCenterConstant.LOCALE_ENGLISH }
                .forEach {
                    tvLocaleLearningEn.visibility = View.VISIBLE
                }

            val name = "${item.givenName} ${item.familyName}"
            tvName.text = name
            tvEmail.text = item.email
            ivPicture.loadCircle(item.picture)
            tvAge.text = item.age.toString()
            when (item.gender) {
                LanguageCenterConstant.GENDER_MALE -> ivGender.setImageResource(R.drawable.ic_male)
                LanguageCenterConstant.GENDER_FEMALE -> ivGender.setImageResource(R.drawable.ic_female)
            }

            setOnClickListener {
                listener?.invoke(item)
            }

            ibMore.setOnClickListener {
                moreListener?.invoke(item)
            }
        }
    }

    override fun getItemCount(): Int = list.size

    fun submitList(list: List<ChatGroupDetail>) = asyncListDiffer.submitList(list)

    fun setListener(listener: ((ChatGroupDetail) -> Unit)?) {
        this.listener = listener
    }

    fun setMoreListener(moreListener: ((ChatGroupDetail) -> Unit)?) {
        this.moreListener = moreListener
    }

    class ChatGroupDetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}
