package com.lc.android.presentation.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lc.android.R
import com.lc.server.models.model.UserInfoLocale
import kotlinx.android.synthetic.main.item_user_info_locale.view.*
import java.util.*

class ProfileLocaleAdapter : RecyclerView.Adapter<ProfileLocaleAdapter.ProfileLocaleViewHolder>() {

    private val list by lazy { mutableListOf<UserInfoLocale>() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileLocaleViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user_info_locale, parent, false)
        return ProfileLocaleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProfileLocaleViewHolder, position: Int) {
        holder.itemView.tvLocale.text = list[position].locale?.toUpperCase(Locale.getDefault())
        when (list[position].locale?.toLowerCase(Locale.getDefault())) {
            "th" -> holder.itemView.ivLocale.setImageResource(R.drawable.ic_thailand)
            "en" -> holder.itemView.ivLocale.setImageResource(R.drawable.ic_united_kingdom)
        }
    }

    override fun getItemCount(): Int = list.size

    fun setList(list: List<UserInfoLocale>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    class ProfileLocaleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}
