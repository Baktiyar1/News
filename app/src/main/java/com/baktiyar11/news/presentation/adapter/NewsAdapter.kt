package com.baktiyar11.news.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.baktiyar11.news.R
import com.baktiyar11.news.databinding.ArticleItemBinding
import com.baktiyar11.news.domain.model.Article
import com.baktiyar11.news.presentation.adapter.NewsAdapter.*
import com.squareup.picasso.Picasso

class NewsAdapter
    : PagingDataAdapter<Article, NewsViewHolder>(NewsDiffItemCallback) {

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val layoutInflater =
            LayoutInflater.from(parent.context).inflate(R.layout.article_item, parent, false)
        val binding = ArticleItemBinding.bind(layoutInflater)
        return NewsViewHolder(binding)
    }

    inner class NewsViewHolder(private var binding: ArticleItemBinding)
        : RecyclerView.ViewHolder(binding.root){
        fun bind(article: Article) {
            binding.apply {
                titleTVID.text = article.title
                titleDescriptionTVID.text = article.description
                sourceTVID.text = article.source.toString()
                howLongAgoWasItTVID.text = article.publishedAt.toString()
                val newsPostersURL = article.urlToImage
                Picasso.get()
                    .load(newsPostersURL)
                    .resize(200, 200)
                    .into(titleIVID)
            }
        }
    }

    private object NewsDiffItemCallback : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.title == newItem.title && oldItem.url == newItem.url
        }
    }

}