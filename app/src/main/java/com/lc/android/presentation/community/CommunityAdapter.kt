package com.lc.android.presentation.community

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lc.android.R
import com.lc.android.util.loadCircle
import com.lc.server.models.model.Community
import com.lc.server.util.LanguageCenterConstant
import kotlinx.android.synthetic.main.item_user_info.view.*

class CommunityAdapter : RecyclerView.Adapter<CommunityAdapter.CommunityViewHolder>() {

    private val list by lazy { mutableListOf<Community>() }
    private var listener: ((Community) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommunityViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user_info, parent, false)
        return CommunityViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommunityViewHolder, position: Int) {
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
    }

    override fun getItemCount(): Int = list.size

    fun setList(list: List<Community>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun setListener(listener: ((Community) -> Unit)?) {
        this.listener = listener
    }

    class CommunityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}
