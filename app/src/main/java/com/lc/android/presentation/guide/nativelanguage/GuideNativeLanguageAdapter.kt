package com.lc.android.presentation.guide.nativelanguage

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

class GuideNativeLanguageAdapter :
    RecyclerView.Adapter<GuideNativeLanguageAdapter.GuideSettingLanguageViewHolder>() {

    private val list by lazy { mutableListOf<UserInfoLocaleItem>() }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GuideSettingLanguageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_guide_setting_locale, parent, false)
        return GuideSettingLanguageViewHolder(view)
    }

    override fun onBindViewHolder(holder: GuideSettingLanguageViewHolder, position: Int) {
        var level = "${list[position].level}%"

        holder.itemView.tvLocale.text = list[position].locale?.toUpperCase(Locale.getDefault())
        holder.itemView.tvLevel.text = level
        holder.itemView.checkBox.isChecked = list[position].isChecked
        holder.itemView.seekBar.progress = list[position].level
        holder.itemView.seekBar.isVisible = list[position].isChecked
        when (list[position].locale?.toLowerCase(Locale.getDefault())) {
            "th" -> holder.itemView.ivLocale.setImageResource(R.drawable.ic_thailand)
            "en" -> holder.itemView.ivLocale.setImageResource(R.drawable.ic_united_kingdom)
            else -> holder.itemView.ivLocale.setImageResource(R.drawable.ic_world)
        }

        holder.itemView.checkBox.setOnCheckedChangeListener { _, b ->
            holder.itemView.seekBar.isVisible = b
        }

        holder.itemView.seekBar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, b: Boolean) {
                level = "$progress%"
                holder.itemView.tvLevel.text = level
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

    inner class GuideSettingLanguageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}
