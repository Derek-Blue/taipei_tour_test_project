package com.ken.taipeitourtestproject.module.service.response.attractions

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AttractionsCategoryResponse(

    @SerialName("id")
    val id: Long? = null,

    @SerialName("name")
    val name: String? = null,
)