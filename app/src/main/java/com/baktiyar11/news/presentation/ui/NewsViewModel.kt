package com.baktiyar11.news.presentation.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.baktiyar11.news.data.RetrofitInstance
import com.baktiyar11.news.data.source.EverythingNewsPageSource
import com.baktiyar11.news.domain.model.Article
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest

enum class ResponseType {
    POPULAR
}

@ExperimentalCoroutinesApi
class NewsViewModel: ViewModel() {

    private var responseBy: MutableLiveData<ResponseType> = MutableLiveData<ResponseType>()
    private var query: MutableLiveData<String> = MutableLiveData()
//    private val newsRepository = NewsRepository()

    init {
        query.value = "us"
    }

    val newsFlow: Flow<PagingData<Article>> by lazy {
        responseBy.asFlow()
            .flatMapLatest {
                initNewsPageSource(
//                    response = it,
                    country = query.value!!)
            }
            .cachedIn(viewModelScope)
    }

    private fun initNewsPageSource(
//        response: ResponseType,
        country: String,
    ): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(
                pageSize = 2,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                EverythingNewsPageSource(
                    newsService = RetrofitInstance.articleApi,
//                    responseType = response,
                    country = country
                )
            }
        ).flow
    }

    fun responseType(newResponse: ResponseType) {
        responseBy.value = newResponse
    }

//    fun searchType( newText: String){
//        responseBy.value = ResponseType.SEARCH_MOVIE
//        query.value = newText
//    }

}