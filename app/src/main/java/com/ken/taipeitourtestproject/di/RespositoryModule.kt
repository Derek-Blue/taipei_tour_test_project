package com.ken.taipeitourtestproject.di

import com.ken.taipeitourtestproject.module.repository.attractions.TaipeiTourAttractionRepository
import com.ken.taipeitourtestproject.module.repository.attractions.TaipeiTourAttractionRepositoryImpl
import com.ken.taipeitourtestproject.module.repository.news.NewsRepository
import com.ken.taipeitourtestproject.module.repository.news.NewsRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {

    factory<TaipeiTourAttractionRepository> {
        TaipeiTourAttractionRepositoryImpl(get())
    }

    factory<NewsRepository> {
        NewsRepositoryImpl(get())
    }
}