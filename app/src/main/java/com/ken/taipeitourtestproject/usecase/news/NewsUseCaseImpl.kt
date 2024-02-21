package com.ken.taipeitourtestproject.usecase.news

import com.ken.taipeitourtestproject.module.repository.attractions.request.RepositoryLanguageType
import com.ken.taipeitourtestproject.module.repository.news.NewsRepository
import com.ken.taipeitourtestproject.screen.home.data.LanguageType
import com.ken.taipeitourtestproject.tools.sharedpreference.MySharedPreferencesManager

class NewsUseCaseImpl(
    private val sharedPreferencesManager: MySharedPreferencesManager,
    private val newsRepository: NewsRepository
): NewsUseCase {

    companion object {
        private const val DEFAULT_START_PAGE = 1
        private const val HOME_PAGE_SHOW_COUNT = 6
    }

    override suspend fun getHomePageNews(): Result<List<UseCaseNews>> {
        val requestLanguage = getRepositoryTypeLanguage()
        return newsRepository.getData(requestLanguage, DEFAULT_START_PAGE).mapCatching { repository ->
            repository.sortedByDescending {
                it.posted.time.time
            }.take(HOME_PAGE_SHOW_COUNT).map { data ->
                UseCaseNews(
                    title = data.title,
                    description = data.description,
                    url = data.url
                )
            }
        }
    }

    private fun getRepositoryTypeLanguage(): RepositoryLanguageType {
        val language = sharedPreferencesManager.languageTypeTag
        return when (LanguageType.fromShowText(language)) {
            LanguageType.ZH_TC -> RepositoryLanguageType.ZH_TC
            LanguageType.ZH_CN -> RepositoryLanguageType.ZH_CN
            LanguageType.EN -> RepositoryLanguageType.EN
            LanguageType.JA -> RepositoryLanguageType.JA
            LanguageType.KO -> RepositoryLanguageType.KO
            LanguageType.TH -> RepositoryLanguageType.TH
            LanguageType.ID -> RepositoryLanguageType.ID
            LanguageType.ES -> RepositoryLanguageType.EN
            LanguageType.VI -> RepositoryLanguageType.VI
        }
    }
}