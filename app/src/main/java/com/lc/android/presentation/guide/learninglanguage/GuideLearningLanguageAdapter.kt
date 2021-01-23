package com.lc.android.presentation.guide.learninglanguage

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

class GuideLearningLanguageAdapter :
    RecyclerView.Adapter<GuideLearningLanguageAdapter.GuideSettingLanguageViewHolder>() {

    val list by lazy { mutableListOf<UserInfoLocaleItem>() }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GuideSettingLanguageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_guide_setting_locale, parent, false)
        return GuideSettingLanguageViewHolder(view)
    }

    override fun onBindViewHolder(holder: GuideSettingLanguageViewHolder, position: Int) {
        val (locale, level, isChecked) = list[position]

        holder.itemView.apply {
            tvLocale.text = locale?.toUpperCase(Locale.getDefault())
            tvLevel.text = "$level%"
            checkBox.isChecked = isChecked
            seekBar.progress = level
            seekBar.isVisible = isChecked
            when (locale?.toLowerCase(Locale.getDefault())) {
                "th" -> ivLocale.setImageResource(R.drawable.ic_thailand)
                "en" -> ivLocale.setImageResource(R.drawable.ic_united_kingdom)
                else -> ivLocale.setImageResource(R.drawable.ic_world)
            }

            checkBox.setOnCheckedChangeListener { _, b ->
                list[position] = list[position].copy(isChecked = b)
                seekBar.isVisible = b
            }

            seekBar.setOnSeekBarChangeListener(object :
                SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, b: Boolean) {
                    list[position] = list[position].copy(level = progress)
                    tvLevel.text = "$progress%"
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                }
            })
        }
    }

    override fun getItemCount(): Int = list.size

    fun setList(localNatives: List<UserInfoLocaleItem>) {
        this.list.clear()
        this.list.addAll(localNatives)
        notifyDataSetChanged()
    }

    inner class GuideSettingLanguageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}
