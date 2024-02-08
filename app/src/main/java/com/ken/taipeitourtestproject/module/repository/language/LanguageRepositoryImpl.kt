package com.ken.taipeitourtestproject.module.repository.language

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.ken.taipeitourtestproject.module.repository.attractions.request.RepositoryLanguageType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LanguageRepositoryImpl(
    private val dataStore: DataStore<Preferences>
): LanguageRepository {

    companion object {
        private const val LANGUAGE_FIELD_KEY = "language_field_key"
    }

    private val key by lazy {
        stringPreferencesKey(LANGUAGE_FIELD_KEY)
    }

    override suspend fun getFlow(): Flow<RepositoryLanguageType> {
        return dataStore.data.map {
            val tag = it[key] ?: RepositoryLanguageType.ZH_TC.tag
            RepositoryLanguageType.fromTag(tag)
        }
    }

    override suspend fun update(type: RepositoryLanguageType) {
        dataStore.edit { it[key] = type.tag }
    }

}