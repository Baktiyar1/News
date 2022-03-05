package com.baktiyar11.news.data.api

import androidx.annotation.IntRange
import com.baktiyar11.news.data.DEFAULT_PAGE_SIZE
import com.baktiyar11.news.data.MAX_PAGE_SIZE
import com.baktiyar11.news.domain.model.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ArticleApi {

    @GET("top-headlines/sources")
    suspend fun getTopNewsByCountry(
        @Query("country") country: String,
        @Query("page") @IntRange(from = 1) page: Int = 1,
        @Query("pageSize") @IntRange(from = 1,
            to = MAX_PAGE_SIZE.toLong()) pageSize: Int = DEFAULT_PAGE_SIZE,
    ): Response<NewsResponse>

}