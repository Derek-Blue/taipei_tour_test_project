package com.ken.taipeitourtestproject.module.service.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AttractionsDataResponse(
    @SerialName("id")
    val id: Long? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("name_zh")
    val nameZh: String? = null,
    @SerialName("open_status")
    val openStatus: Int? = null,
    @SerialName("introduction")
    val introduction: String? = null,
    @SerialName("open_time")
    val openTime: String? = null,
    @SerialName("zipcode")
    val zipcode: String? = null,
    @SerialName("distric")
    val distric: String? = null,
    @SerialName("address")
    val address: String? = null,
    @SerialName("tel")
    val tel: String? = null,
    @SerialName("fax")
    val fax: String? = null,
    @SerialName("email")
    val email: String? = null,
    @SerialName("months")
    val months: String? = null,
    @SerialName("nlat")
    val nlat: Double? = null,
    @SerialName("elong")
    val elong: Double? = null,
    @SerialName("official_site")
    val officialSite: String? = null,
    @SerialName("facebook")
    val facebook: String? = null,
    @SerialName("ticket")
    val ticket: String? = null,
    @SerialName("remind")
    val remind: String? = null,
    @SerialName("staytime")
    val stayTime: String? = null,
    @SerialName("modified")
    val modified: String? = null,
    @SerialName("url")
    val url: String? = null,
    @SerialName("category")
    val category: List<AttractionsCategoryResponse>? = null,
    @SerialName("target")
    val target: List<AttractionsCategoryResponse>? = null,
    @SerialName("service")
    val service: List<AttractionsCategoryResponse>? = null,
    @SerialName("friendly")
    val friendly: List<AttractionsCategoryResponse>? = null,
    @SerialName("images")
    val images: List<AttractionsImageResponse>? = null,
    @SerialName("files")
    val files: List<AttractionsFileResponse>? = null,
    @SerialName("links")
    val links: List<AttractionsLinkResponse>? = null,
)
