package com.ken.taipeitourtestproject.screen.home.data

import java.util.Locale

enum class LanguageType(val showText: String, val locale: Locale) {
    ZH_TC("繁體中文", Locale.TAIWAN),
    ZH_CN("简体中文", Locale.CHINA),
    EN("English", Locale.ENGLISH),
    JA("Japan", Locale.JAPAN),
    KO("Korea", Locale.KOREA),
    ES("Spanish", Locale("es", "ES")),
    ID("Indonesia", Locale("in")),
    TH("Thailand", Locale("th", "TH")),
    VI("Vietnam", Locale("vi", "VI"));

    companion object {

        val SELECT_ITEMS by lazy {
            entries.map { it.showText }.toTypedArray()
        }

        fun fromShowText(text: String) =
            entries.find { it.showText == text } ?: ZH_TC
    }
}