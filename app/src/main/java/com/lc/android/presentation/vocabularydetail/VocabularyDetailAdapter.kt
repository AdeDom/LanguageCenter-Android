package com.lc.android.presentation.vocabularydetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lc.android.R
import com.lc.android.util.loadCircle
import com.lc.server.models.model.Community
import com.lc.server.models.model.Vocabulary
import com.lc.server.util.LanguageCenterConstant
import kotlinx.android.synthetic.main.item_vocabulary_detail.view.*

class VocabularyDetailAdapter :
    RecyclerView.Adapter<VocabularyDetailAdapter.VocabularyDetailViewHolder>() {

    private val asyncListDiffer =
        AsyncListDiffer(this, object : DiffUtil.ItemCallback<Vocabulary>() {
            override fun areItemsTheSame(oldItem: Vocabulary, newItem: Vocabulary): Boolean {
                return oldItem.vocabularyId == newItem.vocabularyId
            }

            override fun areContentsTheSame(oldItem: Vocabulary, newItem: Vocabulary): Boolean {
                return oldItem == newItem
            }
        })

    private val list: List<Vocabulary>
        get() = asyncListDiffer.currentList

    private var userInfoListener: ((Community?) -> Unit)? = null
    private var vocabularyListener: ((String) -> Unit)? = null
    private var translateListener: ((String?) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VocabularyDetailViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_vocabulary_detail, parent, false)
        return VocabularyDetailViewHolder(view)
    }

    override fun onBindViewHolder(holder: VocabularyDetailViewHolder, position: Int) {
        holder.itemView.apply {
            val item = list[position]

            // reference
            if (item.reference != LanguageCenterConstant.GOOGLE_TRANSLATE) {
                ivReference.visibility = View.GONE
            }

            // vocabulary
            tvVocabularyText.text = item.vocabulary
            tvVocabularyText.setOnClickListener {
                vocabularyListener?.invoke(item.vocabulary)
            }

            // translation
            if (item.translations.size == 1) {
                tvTranslationText.text = item.translations.singleOrNull()?.translation
                tvTranslationText.setOnClickListener {
                    translateListener?.invoke(item.translations.singleOrNull()?.translation)
                }
            } else {
                var translateText = ""
                item.translations.forEachIndexed { index, translation ->
                    translateText += "[${index.plus(1)}] ${translation.translation}${if (index == item.translations.size - 1) "" else "\n"}"
                }
                tvTranslationText.text = translateText
            }

            // user info
            if (item.userInfo == null) {
                tvName.visibility = View.GONE
                ibPicture.visibility = View.GONE
            } else {
                val name = "${item.userInfo?.givenName} ${item.userInfo?.familyName}"
                tvName.text = name
                ibPicture.loadCircle(item.userInfo?.picture)

                tvName.setOnClickListener {
                    userInfoListener?.invoke(item.userInfo)
                }

                ibPicture.setOnClickListener {
                    userInfoListener?.invoke(item.userInfo)
                }
            }
        }
    }

    override fun getItemCount(): Int = list.size

    fun submitList(list: List<Vocabulary>) = asyncListDiffer.submitList(list)

    fun userInfoListener(listener: (Community?) -> Unit) {
        this.userInfoListener = listener
    }

    fun vocabularyListener(listener: (String) -> Unit) {
        this.vocabularyListener = listener
    }

    fun translateListener(listener: (String?) -> Unit) {
        this.translateListener = listener
    }

    inner class VocabularyDetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}
