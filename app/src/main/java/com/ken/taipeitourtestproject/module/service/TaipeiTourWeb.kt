package com.ken.taipeitourtestproject.module.service

import com.ken.taipeitourtestproject.module.service.response.attractions.AttractionsResponse
import com.ken.taipeitourtestproject.module.service.response.news.NewsResponse

interface TaipeiTourWeb {

    suspend fun getAttractions(language: String, page: Int): Result<AttractionsResponse>

    suspend fun getNews(language: String, page: Int): Result<NewsResponse>
}