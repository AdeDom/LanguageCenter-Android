package com.lc.android.presentation.vocapbulary

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lc.android.R
import com.lc.android.presentation.model.TranslationParcelable
import kotlinx.android.synthetic.main.item_vocabulary_feedback.view.*

class VocabularyFeedbackAdapter :
    RecyclerView.Adapter<VocabularyFeedbackAdapter.VocabularyFeedbackViewHolder>() {

    private var list = mutableListOf<TranslationParcelable>()
    private var correctListener: ((TranslationParcelable) -> Unit)? = null
    private var incorrectListener: ((TranslationParcelable) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VocabularyFeedbackViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_vocabulary_feedback, parent, false)
        return VocabularyFeedbackViewHolder(view)
    }

    override fun onBindViewHolder(holder: VocabularyFeedbackViewHolder, position: Int) {
        holder.itemView.apply {
            val item = list[position]

            tvTranslation.text = item.translation
            when (item.isCorrect) {
                true -> {
                    ibCorrect.setImageResource(R.drawable.ic_correct_select)
                    ibIncorrect.setImageResource(R.drawable.ic_incorrect_unselect)
                }
                false -> {
                    ibCorrect.setImageResource(R.drawable.ic_correct_unselect)
                    ibIncorrect.setImageResource(R.drawable.ic_incorrect_select)
                }
                null -> {
                    ibCorrect.setImageResource(R.drawable.ic_correct_unselect)
                    ibIncorrect.setImageResource(R.drawable.ic_incorrect_unselect)
                }
            }

            ibCorrect.setOnClickListener {
                correctListener?.invoke(item)
            }

            ibIncorrect.setOnClickListener {
                incorrectListener?.invoke(item)
            }
        }
    }

    override fun getItemCount(): Int = list.size

    fun setList(translations: List<TranslationParcelable>?) {
        if (!translations.isNullOrEmpty()) {
            this.list.clear()
            this.list.addAll(translations)
            notifyDataSetChanged()
        }
    }

    fun setCorrectListener(correctListener: (TranslationParcelable) -> Unit) {
        this.correctListener = correctListener
    }

    fun setIncorrectListener(incorrectListener: (TranslationParcelable) -> Unit) {
        this.incorrectListener = incorrectListener
    }

    inner class VocabularyFeedbackViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}
