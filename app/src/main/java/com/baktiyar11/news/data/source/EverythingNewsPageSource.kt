package com.baktiyar11.news.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.baktiyar11.news.data.MAX_PAGE_SIZE
import com.baktiyar11.news.data.api.ArticleApi
import com.baktiyar11.news.domain.model.Article
import retrofit2.HttpException
import java.lang.Exception

class EverythingNewsPageSource(
    private val newsService: ArticleApi,
//    private val responseType: ResponseType,
    private val country: String,
) : PagingSource<Int, Article>() {

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {

        val page: Int = params.key ?: 1
        val pageSize: Int = params.loadSize.coerceAtMost(MAX_PAGE_SIZE)

        return try {

             val response = newsService.getTopNewsByCountry(country = country, page = page, pageSize = pageSize)

            if (response.isSuccessful) {
                val article = checkNotNull(response.body()).articles
                val nextKey = if (article.size < pageSize) null else (page + 1)
                val prevKey = if (page == 1) null else (page - 1)
                LoadResult.Page(article, prevKey, nextKey)
            } else {
                LoadResult.Error(HttpException(response))
            }
        } catch (e: HttpException) {
            LoadResult.Error(e)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}