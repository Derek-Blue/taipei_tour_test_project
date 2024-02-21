package com.ken.taipeitourtestproject.module.service.response.attractions

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AttractionsResponse(

    @SerialName("total")
    val total: Long? = null,

    @SerialName("data")
    val data: List<AttractionsDataResponse>? = null,
)
