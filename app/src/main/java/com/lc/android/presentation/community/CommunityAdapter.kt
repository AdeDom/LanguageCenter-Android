package com.lc.android.presentation.community

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lc.android.R
import com.lc.android.util.load
import com.lc.server.models.model.UserInfo
import kotlinx.android.synthetic.main.item_user_info.view.*

class CommunityAdapter : RecyclerView.Adapter<CommunityAdapter.CommunityViewHolder>() {

    private val list by lazy { mutableListOf<UserInfo>() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommunityViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user_info, parent, false)
        return CommunityViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommunityViewHolder, position: Int) {
        val (_, email, givenName, familyName, _, picture) = list[position]

        val name = "$givenName $familyName"
        holder.itemView.tvName.text = name
        holder.itemView.tvEmail.text = email
        holder.itemView.ivPicture.load(picture)
    }

    override fun getItemCount(): Int = list.size

    fun setList(list: List<UserInfo>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    class CommunityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}
