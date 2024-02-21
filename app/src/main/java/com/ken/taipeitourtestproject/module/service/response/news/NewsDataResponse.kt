package com.ken.taipeitourtestproject.module.service.response.news

import com.ken.taipeitourtestproject.module.service.response.attractions.AttractionsFileResponse
import com.ken.taipeitourtestproject.module.service.response.attractions.AttractionsLinkResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewsDataResponse(
    @SerialName("id")
    val id: Long? = null,
    @SerialName("title")
    val title: String? = null,
    @SerialName("description")
    val description: String? = null,
    @SerialName("begin")
    val begin: Int? = null,
    @SerialName("end")
    val end: Int? = null,
    @SerialName("posted")
    val posted: String? = null,
    @SerialName("modified")
    val modified: String? = null,
    @SerialName("url")
    val url: String? = null,
    @SerialName("files")
    val files: List<AttractionsFileResponse>? = null,
    @SerialName("links")
    val links: List<AttractionsLinkResponse>? = null,
)
