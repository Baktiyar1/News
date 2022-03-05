package com.baktiyar11.news.domain.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Article(
    @SerializedName("source") val source: NewsSource? = null,
    @SerializedName("title") val title: String = "",
    @SerializedName("url") val url: String? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("author") val author: String? = null,
    @SerializedName("urlToImage") val urlToImage: String? = null,
    @SerializedName("publishedAt") val publishedAt: String? = null,
    @SerializedName("content") val content: String? = null,
) : Serializable