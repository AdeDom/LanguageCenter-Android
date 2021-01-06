package com.lc.android.presentation.userinfo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lc.android.R
import com.lc.android.presentation.model.UserInfoLocaleParcelable
import kotlinx.android.synthetic.main.item_user_info_locale.view.*
import java.util.*

class UserInfoLocaleAdapter :
    RecyclerView.Adapter<UserInfoLocaleAdapter.UserInfoLocaleViewHolder>() {

    private val list by lazy { mutableListOf<UserInfoLocaleParcelable>() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserInfoLocaleViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user_info_locale, parent, false)
        return UserInfoLocaleViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserInfoLocaleViewHolder, position: Int) {
        holder.itemView.tvLocale.text = list[position].locale?.toUpperCase(Locale.getDefault())
        holder.itemView.tvLevel.text = list[position].level.toString()
        when (list[position].locale?.toLowerCase(Locale.getDefault())) {
            "th" -> holder.itemView.ivLocale.setImageResource(R.drawable.ic_thailand)
            "en" -> holder.itemView.ivLocale.setImageResource(R.drawable.ic_united_kingdom)
            else -> holder.itemView.ivLocale.setImageResource(R.drawable.ic_world)
        }
    }

    override fun getItemCount(): Int = list.size

    fun setList(list: List<UserInfoLocaleParcelable>?) {
        if (!list.isNullOrEmpty()) {
            this.list.clear()
            this.list.addAll(list)
            notifyDataSetChanged()
        }
    }

    class UserInfoLocaleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}
