package com.ken.taipeitourtestproject.usecase.attractionlist

import kotlinx.coroutines.flow.Flow

interface AttractionListUseCase {

    suspend fun subscribe(): Flow<List<UseCaseAttraction>>

    suspend fun loadMore()
}