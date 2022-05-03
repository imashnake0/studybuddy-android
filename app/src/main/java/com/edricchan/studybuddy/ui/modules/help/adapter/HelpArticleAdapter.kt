package com.edricchan.studybuddy.ui.modules.help.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import com.edricchan.studybuddy.databinding.HelparticleadapterItemRowBinding
import com.edricchan.studybuddy.interfaces.HelpArticle
import com.edricchan.studybuddy.ui.modules.help.adapter.HelpArticleAdapter.OnItemClickListener

class HelpArticleAdapter(
    private val helpArticlesList: List<HelpArticle>
) : RecyclerView.Adapter<HelpArticleAdapter.Holder>() {
    private var listener: OnItemClickListener? = null

    var onItemClickListener by ::listener

    fun setOnItemClickListener(listener: (article: HelpArticle, position: Int) -> Unit) {
        onItemClickListener =
            OnItemClickListener { article, position -> listener.invoke(article, position) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = Holder(
        HelparticleadapterItemRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(helpArticlesList[position])
    }

    override fun getItemCount() = helpArticlesList.size

    inner class Holder(private val binding: HelparticleadapterItemRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: HelpArticle) {
            itemView.apply {
                isEnabled = !article.isDisabled
                isGone = article.isHidden
                setOnClickListener { listener?.onItemClick(article, bindingAdapterPosition) }
            }

            val (articleDesc, _, articleTitle) = article
            binding.apply {
                descTextView.text = articleDesc
                nameTextView.text = articleTitle
            }
        }
    }

    /**
     * The on item click listener
     */
    fun interface OnItemClickListener {
        /**
         * Called when an item is clicked on
         *
         * @param article  The article
         * @param position The position of the item
         */
        fun onItemClick(article: HelpArticle, position: Int)
    }
}
