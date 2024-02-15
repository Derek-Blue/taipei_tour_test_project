package com.ken.taipeitourtestproject.di

import com.ken.taipeitourtestproject.usecase.attractionlist.AttractionListUseCase
import com.ken.taipeitourtestproject.usecase.attractionlist.AttractionListUseCaseImpl
import org.koin.dsl.module

val useCaseModule = module {

    factory<AttractionListUseCase> {
        AttractionListUseCaseImpl(get(), get())
    }
}