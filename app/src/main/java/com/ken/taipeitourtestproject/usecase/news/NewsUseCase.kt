package com.ken.taipeitourtestproject.usecase.news

interface NewsUseCase {

    suspend fun getHomePageNews(): Result<List<UseCaseNews>>
}