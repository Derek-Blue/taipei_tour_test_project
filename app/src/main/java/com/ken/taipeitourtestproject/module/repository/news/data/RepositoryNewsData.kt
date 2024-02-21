package com.ken.taipeitourtestproject.module.repository.news.data

import com.ken.taipeitourtestproject.module.repository.attractions.data.RepositoryAttractionsFile
import com.ken.taipeitourtestproject.module.repository.attractions.data.RepositoryAttractionsLink
import java.util.Calendar

data class RepositoryNewsData(
    val id: Long,
    val title: String,
    val description: String,
    val begin: Int,
    val end: Int,
    val posted: Calendar,
    val modified: String,
    val url: String,
    val files: List<RepositoryAttractionsFile>,
    val links: List<RepositoryAttractionsLink>,
)
