package com.ken.taipeitourtestproject.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.ken.taipeitourtestproject.module.service.TaipeiTourService
import com.ken.taipeitourtestproject.module.service.TaipeiTourWeb
import com.ken.taipeitourtestproject.module.service.TaipeiTourWebImpl
import kotlinx.serialization.json.Json
import okhttp3.ConnectionSpec
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

const val BASE_URL = "https://www.travel.taipei/"

val serviceModule = module {

    single { providerRetrofit2(get()) }

    single<TaipeiTourService> {
        get<Retrofit>().create(TaipeiTourService::class.java)
    }

    factory<TaipeiTourWeb> {
        TaipeiTourWebImpl(get())
    }
}

fun providerRetrofit2(kotlinJson: Json): Retrofit {
    val okHttpClient = createOkhttp()
    val contentType = "application/json".toMediaType()
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(kotlinJson.asConverterFactory(contentType))
        .client(okHttpClient)
        .build()
}

fun createOkhttp(): OkHttpClient {
    return OkHttpClient.Builder()
        .connectionSpecs(listOf(ConnectionSpec.COMPATIBLE_TLS, ConnectionSpec.CLEARTEXT))
        .connectTimeout(30L, TimeUnit.SECONDS)
        .callTimeout(30L, TimeUnit.SECONDS)
        .readTimeout(30L, TimeUnit.SECONDS)
        .writeTimeout(30L, TimeUnit.SECONDS)
        .build()
}