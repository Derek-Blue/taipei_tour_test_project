package com.ken.taipeitourtestproject.usecase.attractionlist

data class UseCaseAttraction(
    val id: Long,
    val name: String,
    val introduction: String,
    val openTime: String,
    val address: String,
    val tel: String,
    val email: String,
    val lastUpdateTime: String,
    val url: String,
    val images: List<String>,
)
