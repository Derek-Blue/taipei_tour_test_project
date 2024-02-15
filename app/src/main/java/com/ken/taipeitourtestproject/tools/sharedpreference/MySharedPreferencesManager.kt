package com.ken.taipeitourtestproject.tools.sharedpreference

import android.content.Context

class MySharedPreferencesManager(context: Context) {

    companion object {
        private const val sharedPreferencesKey = "MySharedPreferencesManager_key"

        private var INSTANCE: MySharedPreferencesManager? = null

        fun getInstance(context: Context): MySharedPreferencesManager {
            return synchronized(this) {
                INSTANCE ?: MySharedPreferencesManager(context.applicationContext).also {
                    INSTANCE = it
                }
            }
        }
    }

    private val baseSharedPreferences = context.getSharedPreferences(
        sharedPreferencesKey, Context.MODE_PRIVATE
    )

    private val languageTypeKey = "language_type_key"
    var languageTypeTag: String
        get() = getSharedPreferencesValue(languageTypeKey, languageTypeKey)
        set(value) {
            setSharedPreferencesValue(languageTypeKey, value)
        }

    /**
     * get function
     */
    private fun getSharedPreferencesValue(key: String, defaultValue: String): String {
        return baseSharedPreferences.getString(key, defaultValue) ?: defaultValue
    }

    /**
     *  set function
     */
    private fun <Type> setSharedPreferencesValue(key: String, para: Type) {
        when (para) {
            is Int -> baseSharedPreferences.edit().putInt(
                key,
                para
            ).apply()

            is String -> baseSharedPreferences.edit().putString(
                key,
                para
            ).apply()

            is Long -> baseSharedPreferences.edit().putLong(
                key,
                para
            ).apply()

            is Boolean -> baseSharedPreferences.edit().putBoolean(
                key,
                para
            ).apply()

            is Float -> baseSharedPreferences.edit().putFloat(
                key,
                para
            ).apply()
        }
    }

}