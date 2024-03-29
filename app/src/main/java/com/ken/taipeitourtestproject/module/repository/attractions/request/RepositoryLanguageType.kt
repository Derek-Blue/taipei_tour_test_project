package com.ken.taipeitourtestproject.module.repository.attractions.request

enum class RepositoryLanguageType(val tag: String) {
    ZH_TC("zh-tw"),
    ZH_CN("zh-cn"),
    EN("en"),
    JA("ja"),
    KO("ko"),
    ES("es"),
//    ID("id"), //api 目前顯示無效參數
    ID("en"),
    TH("th"),
    VI("vi");

    companion object {
        fun fromTag(tag: String): RepositoryLanguageType {
            return entries.find { it.tag == tag } ?: ZH_TC
        }
    }
}