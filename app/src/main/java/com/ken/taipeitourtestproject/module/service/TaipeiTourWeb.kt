package com.ken.taipeitourtestproject.module.service

import com.ken.taipeitourtestproject.module.service.response.AttractionsResponse

interface TaipeiTourWeb {

    suspend fun getAttractions(language: String, page: Int): Result<AttractionsResponse>
}