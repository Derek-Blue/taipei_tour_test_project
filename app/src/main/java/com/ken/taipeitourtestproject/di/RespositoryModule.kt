package com.ken.taipeitourtestproject.di

import com.ken.taipeitourtestproject.module.repository.attractions.TaipeiTourAttractionRepository
import com.ken.taipeitourtestproject.module.repository.attractions.TaipeiTourAttractionRepositoryImpl
import com.ken.taipeitourtestproject.module.repository.language.LanguageRepository
import com.ken.taipeitourtestproject.module.repository.language.LanguageRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {

    factory<TaipeiTourAttractionRepository> {
        TaipeiTourAttractionRepositoryImpl(get())
    }

    factory<LanguageRepository> {
        LanguageRepositoryImpl(get())
    }
}