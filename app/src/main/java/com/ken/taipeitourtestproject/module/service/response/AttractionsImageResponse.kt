package com.ken.taipeitourtestproject.module.service.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AttractionsImageResponse(

    @SerialName("src")
    val src: String? = null,

    @SerialName("subject")
    val subject: String? = null,

    @SerialName("ext")
    val ext: String? = null,
)