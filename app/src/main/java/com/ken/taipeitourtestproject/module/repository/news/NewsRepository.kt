package com.ken.taipeitourtestproject.module.repository.news

import com.ken.taipeitourtestproject.module.repository.attractions.request.RepositoryLanguageType
import com.ken.taipeitourtestproject.module.repository.news.data.RepositoryNewsData

interface NewsRepository {

    suspend fun getData(
        languageType: RepositoryLanguageType,
        page: Int
    ): Result<List<RepositoryNewsData>>
}