package com.ken.taipeitourtestproject.di

import com.ken.taipeitourtestproject.screen.MainViewModel
import com.ken.taipeitourtestproject.screen.attractioninfo.AttractionInfoViewModel
import com.ken.taipeitourtestproject.screen.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        MainViewModel(get())
    }

    viewModel {
        HomeViewModel(get(), get())
    }

    viewModel {
        AttractionInfoViewModel()
    }
}