package com.ken.taipeitourtestproject.module.repository.news

import android.annotation.SuppressLint
import com.ken.taipeitourtestproject.module.repository.attractions.data.RepositoryAttractionsFile
import com.ken.taipeitourtestproject.module.repository.attractions.data.RepositoryAttractionsLink
import com.ken.taipeitourtestproject.module.repository.attractions.request.RepositoryLanguageType
import com.ken.taipeitourtestproject.module.repository.news.data.RepositoryNewsData
import com.ken.taipeitourtestproject.module.service.TaipeiTourWeb
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar

class NewsRepositoryImpl(
    private val taipeiTourWeb: TaipeiTourWeb,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
): NewsRepository {

    companion object {
        private const val REMOTE_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss"
        @SuppressLint("SimpleDateFormat")
        private val REMOTE_DATE_FORMAT = SimpleDateFormat(REMOTE_DATE_PATTERN)
    }

    override suspend fun getData(
        languageType: RepositoryLanguageType,
        page: Int
    ): Result<List<RepositoryNewsData>> {
        return withContext(defaultDispatcher) {
            taipeiTourWeb.getNews(languageType.tag, page).mapCatching { response ->
                response.data?.mapNotNull { data ->
                    val postedDate = formatDate(data.posted) ?:  return@mapNotNull null
                    RepositoryNewsData(
                        id = data.id ?: 0L,
                        title = data.title ?: return@mapNotNull null,
                        description = data.description ?: return@mapNotNull null,
                        begin = data.begin ?: 0,
                        end = data.end ?: 0,
                        posted = postedDate,
                        modified = data.modified ?: "",
                        url = data.url ?: "",
                        files = data.files?.mapNotNull filesMap@ {
                            RepositoryAttractionsFile(
                                src = it.src ?: return@filesMap null,
                                subject = it.subject ?: "",
                                ext = it.ext ?: ""
                            )
                        } ?: emptyList(),
                        links = data.links?.mapNotNull linksMap@ {
                            RepositoryAttractionsLink(
                                src = it.src ?: return@linksMap null,
                                subject = it.subject ?: ""
                            )
                        } ?: emptyList(),
                    )
                } ?: emptyList()
            }
        }
    }

    private fun formatDate(dateString: String?): Calendar? {
        return if (dateString.isNullOrBlank()) {
            null
        } else {
            val formatString = if (dateString.contains("+08:00")) {
                dateString.replace("+08:00", "")
            } else {
                dateString
            }
            try {
                val date = REMOTE_DATE_FORMAT.parse(formatString)
                date?.let {
                    Calendar.getInstance().apply {
                        time = date
                    }
                }
            } catch (e: Exception) {
                null
            }
        }
    }
}