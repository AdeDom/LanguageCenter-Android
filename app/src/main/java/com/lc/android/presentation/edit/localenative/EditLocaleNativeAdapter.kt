package com.lc.android.presentation.edit.localenative

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.lc.android.R
import com.lc.library.presentation.model.UserInfoLocaleItem
import kotlinx.android.synthetic.main.item_guide_setting_locale.view.*
import java.util.*

class EditLocaleNativeAdapter :
    RecyclerView.Adapter<EditLocaleNativeAdapter.EditLocaleNativeViewHolder>() {

    val list by lazy { mutableListOf<UserInfoLocaleItem>() }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EditLocaleNativeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_guide_setting_locale, parent, false)
        return EditLocaleNativeViewHolder(view)
    }

    override fun onBindViewHolder(holder: EditLocaleNativeViewHolder, position: Int) {
        val (locale, level, isChecked) = list[position]

        holder.itemView.tvLocale.text = locale?.toUpperCase(Locale.getDefault())
        holder.itemView.tvLevel.text = "$level%"
        holder.itemView.checkBox.isChecked = isChecked
        holder.itemView.seekBar.progress = level
        holder.itemView.seekBar.isVisible = isChecked
        when (locale?.toLowerCase(Locale.getDefault())) {
            "th" -> holder.itemView.ivLocale.setImageResource(R.drawable.ic_thailand)
            "en" -> holder.itemView.ivLocale.setImageResource(R.drawable.ic_united_kingdom)
            else -> holder.itemView.ivLocale.setImageResource(R.drawable.ic_world)
        }

        holder.itemView.checkBox.setOnCheckedChangeListener { _, b ->
            list[holder.adapterPosition] = list[holder.adapterPosition].copy(isChecked = b)
            holder.itemView.seekBar.isVisible = b
        }

        holder.itemView.seekBar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, b: Boolean) {
                list[holder.adapterPosition] = list[holder.adapterPosition].copy(level = progress)
                holder.itemView.tvLevel.text = "$progress%"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
    }

    override fun getItemCount(): Int = list.size

    fun setList(localNatives: List<UserInfoLocaleItem>) {
        this.list.clear()
        this.list.addAll(localNatives)
        notifyDataSetChanged()
    }

    inner class EditLocaleNativeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}
