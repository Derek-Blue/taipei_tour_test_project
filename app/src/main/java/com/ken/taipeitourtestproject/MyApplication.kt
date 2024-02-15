package com.ken.taipeitourtestproject

import android.app.Application
import com.ken.taipeitourtestproject.di.baseModule
import com.ken.taipeitourtestproject.di.repositoryModule
import com.ken.taipeitourtestproject.di.serviceModule
import com.ken.taipeitourtestproject.di.useCaseModule
import com.ken.taipeitourtestproject.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyApplication)
            modules(
                listOf(
                    baseModule,
                    serviceModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}