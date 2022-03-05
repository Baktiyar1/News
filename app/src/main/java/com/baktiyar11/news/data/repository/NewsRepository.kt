package com.baktiyar11.news.data.repository

import com.baktiyar11.news.domain.model.NewsResponse
import com.baktiyar11.news.data.RetrofitInstance
import retrofit2.Response

class NewsRepository {

    suspend fun getTopNewsByCountry(country: String): Response<NewsResponse> {
        return RetrofitInstance.articleApi.getTopNewsByCountry(country = country)
    }

//    fun getCategoryList(category: String): Response<List<CategoryNews>> {
//        return List<CategoryNews>
//    }

}