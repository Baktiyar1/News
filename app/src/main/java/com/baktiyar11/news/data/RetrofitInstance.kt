package com.baktiyar11.news.data

import com.baktiyar11.news.data.api.ArticleApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val API_KEY = "4bd3f35fb08541a0b4c22d86dbd78105"
const val BASE_URL = "https://newsapi.org/v2/"
const val MAX_PAGE_SIZE = 20
const val DEFAULT_PAGE_SIZE = 1


object RetrofitInstance {

    private val requestInterceptorActors = Interceptor { chain ->
        val url = chain.request()
            .url
            .newBuilder()
            .addQueryParameter("apiKey",
                API_KEY)
            .build()

        val request = chain.request()
            .newBuilder()
            .url(url)
            .build()

        return@Interceptor chain.proceed(request)
    }

    private fun okHttpClient(): OkHttpClient {
        val httpLoginInterceptor = HttpLoggingInterceptor()
        httpLoginInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(requestInterceptorActors)
            .addInterceptor(httpLoginInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val articleApi: ArticleApi by lazy {
        retrofit.create(ArticleApi::class.java)
    }

}