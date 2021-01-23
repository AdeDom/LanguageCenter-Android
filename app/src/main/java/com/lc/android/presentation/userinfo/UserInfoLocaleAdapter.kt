package com.lc.android.presentation.userinfo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lc.android.R
import com.lc.android.presentation.model.UserInfoLocaleParcelable
import kotlinx.android.synthetic.main.item_user_info_locale.view.*
import java.util.*

class UserInfoLocaleAdapter :
    RecyclerView.Adapter<UserInfoLocaleAdapter.UserInfoLocaleViewHolder>() {

    private val list: MutableList<UserInfoLocaleParcelable>
        get() = asyncListDiffer.currentList

    private val asyncListDiffer =
        AsyncListDiffer(this, object : DiffUtil.ItemCallback<UserInfoLocaleParcelable>() {
            override fun areItemsTheSame(
                oldItem: UserInfoLocaleParcelable,
                newItem: UserInfoLocaleParcelable
            ): Boolean {
                return oldItem.locale == newItem.locale
            }

            override fun areContentsTheSame(
                oldItem: UserInfoLocaleParcelable,
                newItem: UserInfoLocaleParcelable
            ): Boolean {
                return oldItem == newItem
            }
        })

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserInfoLocaleViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user_info_locale, parent, false)
        return UserInfoLocaleViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserInfoLocaleViewHolder, position: Int) {
        val item = list[position]

        holder.itemView.apply {
            tvLocale.text = item.locale?.toUpperCase(Locale.getDefault())
            tvLevel.text = item.level.toString()
            when (item.locale?.toLowerCase(Locale.getDefault())) {
                "th" -> ivLocale.setImageResource(R.drawable.ic_thailand)
                "en" -> ivLocale.setImageResource(R.drawable.ic_united_kingdom)
                else -> ivLocale.setImageResource(R.drawable.ic_world)
            }
        }
    }

    override fun getItemCount(): Int = list.size

    fun submitList(list: List<UserInfoLocaleParcelable>?) = asyncListDiffer.submitList(list)

    class UserInfoLocaleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}
