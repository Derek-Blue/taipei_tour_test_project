package com.ken.taipeitourtestproject.module.service

import com.ken.taipeitourtestproject.module.service.response.AttractionsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TaipeiTourService {

    @GET("open-api/{language}/Attractions/All")
    suspend fun getAttractions(
        @Path("language") language: String,
        @Query("page") page: Int
    ): Response<AttractionsResponse>
}