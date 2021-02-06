package com.lc.android.presentation.vocapbulary

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lc.android.R
import com.lc.server.models.model.VocabularyGroup
import kotlinx.android.synthetic.main.item_vocabulary_main.view.*

class VocabularyAdapter : RecyclerView.Adapter<VocabularyAdapter.VocabularyViewHolder>() {

    private val asyncListDiffer =
        AsyncListDiffer(this, object : DiffUtil.ItemCallback<VocabularyGroup>() {
            override fun areItemsTheSame(
                oldItem: VocabularyGroup,
                newItem: VocabularyGroup
            ): Boolean {
                return oldItem.vocabularyGroupId == newItem.vocabularyGroupId
            }

            override fun areContentsTheSame(
                oldItem: VocabularyGroup,
                newItem: VocabularyGroup
            ): Boolean {
                return oldItem == newItem
            }
        })

    private val list: List<VocabularyGroup>
        get() = asyncListDiffer.currentList

    private var listener: ((VocabularyGroup) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VocabularyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_vocabulary_main, parent, false)
        return VocabularyViewHolder(view)
    }

    override fun onBindViewHolder(holder: VocabularyViewHolder, position: Int) {
        holder.itemView.apply {
            val item = list[position]

            tvVocabularyMain.text = item.vocabularyGroupName

            setOnClickListener {
                listener?.invoke(item)
            }
        }
    }

    override fun getItemCount(): Int = list.size

    fun submitList(list: List<VocabularyGroup>) = asyncListDiffer.submitList(list)

    fun setListener(listener: (VocabularyGroup) -> Unit) {
        this.listener = listener
    }

    inner class VocabularyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}
