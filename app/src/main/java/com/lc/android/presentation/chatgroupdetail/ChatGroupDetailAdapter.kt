package com.lc.android.presentation.chatgroupdetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lc.android.R
import com.lc.android.util.loadCircle
import com.lc.server.models.model.ChatGroupDetail
import com.lc.server.util.LanguageCenterConstant
import kotlinx.android.synthetic.main.item_chat_group_detail.view.*

class ChatGroupDetailAdapter :
    RecyclerView.Adapter<ChatGroupDetailAdapter.ChatGroupDetailViewHolder>() {

    private val list by lazy { mutableListOf<ChatGroupDetail>() }
    private var listener: ((ChatGroupDetail) -> Unit)? = null
    private var moreListener: ((ChatGroupDetail) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatGroupDetailViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_chat_group_detail, parent, false)
        return ChatGroupDetailViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatGroupDetailViewHolder, position: Int) {
        val item = list[position]

        // set visibility
        if (item.email.isNullOrBlank()) holder.itemView.tvEmail.visibility = View.GONE
        if (item.gender.isNullOrBlank()) holder.itemView.ivGender.visibility = View.GONE
        if (item.age == null) holder.itemView.tvAge.visibility = View.GONE
        if (item.givenName.isNullOrBlank() && item.familyName.isNullOrBlank()) {
            holder.itemView.tvName.visibility = View.GONE
        }
        if (item.localNatives.isNullOrEmpty()) {
            holder.itemView.layoutNative.visibility = View.GONE
        }
        if (item.localLearnings.isNullOrEmpty()) {
            holder.itemView.layoutLearning.visibility = View.GONE
        }
        item.localNatives.filter { it.locale == LanguageCenterConstant.LOCALE_THAI }.forEach {
            holder.itemView.tvLocaleNativeTh.visibility = View.VISIBLE
        }
        item.localNatives.filter { it.locale == LanguageCenterConstant.LOCALE_ENGLISH }.forEach {
            holder.itemView.tvLocaleNativeEn.visibility = View.VISIBLE
        }
        item.localLearnings.filter { it.locale == LanguageCenterConstant.LOCALE_THAI }.forEach {
            holder.itemView.tvLocaleLearningTh.visibility = View.VISIBLE
        }
        item.localLearnings.filter { it.locale == LanguageCenterConstant.LOCALE_ENGLISH }.forEach {
            holder.itemView.tvLocaleLearningEn.visibility = View.VISIBLE
        }

        val name = "${item.givenName} ${item.familyName}"
        holder.itemView.tvName.text = name
        holder.itemView.tvEmail.text = item.email
        holder.itemView.ivPicture.loadCircle(item.picture)
        holder.itemView.tvAge.text = item.age.toString()
        when (item.gender) {
            LanguageCenterConstant.GENDER_MALE -> holder.itemView.ivGender.setImageResource(R.drawable.ic_male)
            LanguageCenterConstant.GENDER_FEMALE -> holder.itemView.ivGender.setImageResource(R.drawable.ic_female)
        }

        holder.itemView.setOnClickListener {
            listener?.invoke(item)
        }

        holder.itemView.ibMore.setOnClickListener {
            moreListener?.invoke(item)
        }
    }

    override fun getItemCount(): Int = list.size

    fun setList(list: List<ChatGroupDetail>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun setListener(listener: ((ChatGroupDetail) -> Unit)?) {
        this.listener = listener
    }

    fun setMoreListener(moreListener: ((ChatGroupDetail) -> Unit)?) {
        this.moreListener = moreListener
    }

    class ChatGroupDetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}
