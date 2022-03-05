package com.baktiyar11.news.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.baktiyar11.news.databinding.ErrorItemBinding
import com.baktiyar11.news.databinding.ProgressItemBinding

class NewsLoaderStateAdapter : LoadStateAdapter<NewsLoaderStateAdapter.ItemViewHolder>() {
    override fun getStateViewType(loadState: LoadState) = when (loadState) {
        is LoadState.NotLoading -> error("Not supported")
        LoadState.Loading -> PROGRESS
        is LoadState.Error -> ERROR
    }

    override fun onBindViewHolder(holder: ItemViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ItemViewHolder {
        return when (loadState) {
            LoadState.Loading -> ProgressViewHolder(LayoutInflater.from(parent.context), parent)
            is LoadState.Error -> ErrorViewHolder(LayoutInflater.from(parent.context), parent)
            is LoadState.NotLoading -> error("Not supported")
        }
    }

    private companion object {
        private const val ERROR = 1
        private const val PROGRESS = 0
    }

    abstract class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(loadState: LoadState)
    }

    class ProgressViewHolder internal constructor(
        binding: ProgressItemBinding,
    ) : ItemViewHolder(binding.root) {
        override fun bind(loadState: LoadState) {
            // Do nothing
        }

        companion object {
            operator fun invoke(
                layoutInflater: LayoutInflater,
                parent: ViewGroup? = null,
                attachToRoot: Boolean = false,
            ): ProgressViewHolder {
                return ProgressViewHolder(
                    ProgressItemBinding.inflate(
                        layoutInflater,
                        parent,
                        attachToRoot
                    )
                )
            }
        }
    }

    class ErrorViewHolder internal constructor(
        private val binding: ErrorItemBinding,
    ) : ItemViewHolder(binding.root) {
        override fun bind(loadState: LoadState) {
            require(loadState is LoadState.Error)
            binding.errorMessage.text = loadState.error.localizedMessage
        }

        companion object {
            operator fun invoke(
                layoutInflater: LayoutInflater,
                parent: ViewGroup? = null,
                attachToRoot: Boolean = false,
            ): ErrorViewHolder {
                return ErrorViewHolder(
                    ErrorItemBinding.inflate(
                        layoutInflater,
                        parent,
                        attachToRoot
                    )
                )
            }
        }
    }
}