package com.ken.taipeitourtestproject.module.service

import com.ken.taipeitourtestproject.module.service.response.attractions.AttractionsResponse
import com.ken.taipeitourtestproject.module.service.response.news.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface TaipeiTourService {

    @Headers("Accept: application/json")
    @GET("open-api/{language}/Attractions/All")
    suspend fun getAttractions(
        @Path("language") language: String,
        @Query("page") page: Int
    ): Response<AttractionsResponse>

    @Headers("Accept: application/json")
    @GET("open-api/{language}/Events/News")
    suspend fun getNews(
        @Path("language") language: String,
        @Query("page") page: Int
    ): Response<NewsResponse>
}