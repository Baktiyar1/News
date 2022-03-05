package com.baktiyar11.news.domain.model

import androidx.annotation.IntRange
import com.google.gson.annotations.SerializedName

data class NewsResponse(
    @SerializedName("status") val status: String,
    @SerializedName("totalResults") @IntRange(from = 1) val totalResults: Int,
    @SerializedName("message") val message: String? = null,
    @SerializedName("articles") val articles: List<Article>,
)
