package com.baktiyar11.news.domain.model

import com.google.gson.annotations.SerializedName

data class NewsSource(
    @SerializedName("id") val id: String?,
    @SerializedName("name") val name: String,
)
