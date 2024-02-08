package com.ken.taipeitourtestproject.module.repository.attractions

import com.ken.taipeitourtestproject.module.repository.attractions.data.RepositoryAttractionsData
import com.ken.taipeitourtestproject.module.repository.attractions.request.RepositoryLanguageType

interface TaipeiTourAttractionRepository {

    suspend fun getData(
        languageType: RepositoryLanguageType,
        page: Int
    ): Result<List<RepositoryAttractionsData>>
}