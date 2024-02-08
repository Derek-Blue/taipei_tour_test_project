package com.ken.taipeitourtestproject.module.repository.language

import com.ken.taipeitourtestproject.module.repository.attractions.request.RepositoryLanguageType
import kotlinx.coroutines.flow.Flow

interface LanguageRepository {

    suspend fun getFlow(): Flow<RepositoryLanguageType>

    suspend fun update(type: RepositoryLanguageType)
}