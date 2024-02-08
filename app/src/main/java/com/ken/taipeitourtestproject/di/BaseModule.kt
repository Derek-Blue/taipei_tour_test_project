package com.ken.taipeitourtestproject.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import java.io.File

private const val DATA_STORE_NAME = "taipeitourtestproject_data_store"

val baseModule = module {

    single { provideKotlinJson() }

    single { createDataStore(androidContext()) }
}

@OptIn(ExperimentalSerializationApi::class)
fun provideKotlinJson(): Json {
    return Json {
        encodeDefaults = true
        ignoreUnknownKeys = true
        isLenient = true
        allowStructuredMapKeys = true
        prettyPrint = true
        explicitNulls = true
        coerceInputValues = true
        useArrayPolymorphism = true
        classDiscriminator = "type"
        allowSpecialFloatingPointValues = false
        useAlternativeNames = true
    }
}

fun createDataStore(context: Context): DataStore<Preferences> {
    return context.createDefaultDataStore(DATA_STORE_NAME)
}

private fun Context.createDefaultDataStore(storeName: String): DataStore<Preferences> {
    return PreferenceDataStoreFactory.create(
        produceFile = {
            File(this.filesDir, "datastore/$storeName.preferences_pb")
        }
    )
}