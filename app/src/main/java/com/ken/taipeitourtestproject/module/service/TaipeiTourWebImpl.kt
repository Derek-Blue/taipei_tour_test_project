package com.ken.taipeitourtestproject.module.service

import com.ken.taipeitourtestproject.module.ext.checkSuccessful
import com.ken.taipeitourtestproject.module.ext.requireBody
import com.ken.taipeitourtestproject.module.service.response.attractions.AttractionsResponse
import com.ken.taipeitourtestproject.module.service.response.news.NewsResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TaipeiTourWebImpl(
    private val service: TaipeiTourService,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO,
): TaipeiTourWeb {

    override suspend fun getAttractions(language: String, page: Int): Result<AttractionsResponse> {
        return withContext(defaultDispatcher) {
            runCatching {
                service.getAttractions(language, page)
                    .checkSuccessful()
                    .requireBody()
            }
        }
    }

    override suspend fun getNews(language: String, page: Int): Result<NewsResponse> {
        return withContext(defaultDispatcher) {
            runCatching {
                service.getNews(language, page)
                    .checkSuccessful()
                    .requireBody()
            }
        }
    }
}