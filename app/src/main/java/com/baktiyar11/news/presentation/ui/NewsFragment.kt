package com.baktiyar11.news.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.baktiyar11.news.databinding.FragmentNewsBinding
import com.baktiyar11.news.presentation.adapter.NewsAdapter
import com.baktiyar11.news.presentation.adapter.NewsLoaderStateAdapter
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest

@DelicateCoroutinesApi
@ExperimentalCoroutinesApi
class NewsFragment : Fragment() {

    private val binding: FragmentNewsBinding by lazy {
        FragmentNewsBinding.inflate(layoutInflater)
    }
    private val newsViewModel = NewsViewModel()
    private val adapterNews = NewsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        binding.recNews.layoutManager = GridLayoutManager(requireContext(), 2)
        newsViewModel.responseType(ResponseType.POPULAR)

        binding.recNews.adapter = adapterNews
            .withLoadStateHeaderAndFooter(
                footer = NewsLoaderStateAdapter(),
                header = NewsLoaderStateAdapter()
            )

        GlobalScope.launch(Dispatchers.Main) {
            newsViewModel.newsFlow.collectLatest(adapterNews::submitData)
        }
        adapterNews.addLoadStateListener { state: CombinedLoadStates ->
            binding.recNews.isVisible = state.refresh != LoadState.Loading
            binding.newsErrorPBID.isVisible = state.refresh == LoadState.Loading
        }

        return binding.root

    }
}