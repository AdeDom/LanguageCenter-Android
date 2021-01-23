package com.lc.android.presentation.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lc.android.R
import com.lc.server.models.model.UserInfoLocale
import kotlinx.android.synthetic.main.item_user_info_locale.view.*
import java.util.*

class ProfileLocaleAdapter : RecyclerView.Adapter<ProfileLocaleAdapter.ProfileLocaleViewHolder>() {

    private val list: MutableList<UserInfoLocale>
        get() = asyncListDiffer.currentList

    private val asyncListDiffer =
        AsyncListDiffer(this, object : DiffUtil.ItemCallback<UserInfoLocale>() {
            override fun areItemsTheSame(
                oldItem: UserInfoLocale,
                newItem: UserInfoLocale
            ): Boolean {
                return oldItem.locale == newItem.locale
            }

            override fun areContentsTheSame(
                oldItem: UserInfoLocale,
                newItem: UserInfoLocale
            ): Boolean {
                return oldItem == newItem
            }
        })

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileLocaleViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user_info_locale, parent, false)
        return ProfileLocaleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProfileLocaleViewHolder, position: Int) {
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

    fun submitList(list: List<UserInfoLocale>) = asyncListDiffer.submitList(list)

    class ProfileLocaleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}
