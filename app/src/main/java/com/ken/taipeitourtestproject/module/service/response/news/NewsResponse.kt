package com.ken.taipeitourtestproject.module.service.response.news

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewsResponse(

    @SerialName("total")
    val total: Long? = null,

    @SerialName("data")
    val data: List<NewsDataResponse>? = null,
)
