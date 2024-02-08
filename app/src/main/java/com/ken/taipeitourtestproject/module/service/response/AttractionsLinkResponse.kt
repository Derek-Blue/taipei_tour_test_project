package com.ken.taipeitourtestproject.module.service.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AttractionsLinkResponse(

    @SerialName("src")
    val src: String? = null,

    @SerialName("subject")
    val subject: String? = null,
)