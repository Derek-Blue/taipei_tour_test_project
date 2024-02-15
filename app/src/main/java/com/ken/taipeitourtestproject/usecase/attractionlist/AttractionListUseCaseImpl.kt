package com.ken.taipeitourtestproject.usecase.attractionlist

import com.ken.taipeitourtestproject.module.repository.attractions.TaipeiTourAttractionRepository
import com.ken.taipeitourtestproject.module.repository.attractions.request.RepositoryLanguageType
import com.ken.taipeitourtestproject.screen.home.data.LanguageType
import com.ken.taipeitourtestproject.tools.sharedpreference.MySharedPreferencesManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.scan

class AttractionListUseCaseImpl(
    private val sharedPreferencesManager: MySharedPreferencesManager,
    private val taipeiTourAttractionRepository: TaipeiTourAttractionRepository
): AttractionListUseCase {

    companion object {
        private const val DEFAULT_START_PAGE = 1
    }

    private var nextPage: Int = DEFAULT_START_PAGE

    private val loadMoreFlow = MutableSharedFlow<Unit>()

    override suspend fun subscribe(): Flow<List<UseCaseAttraction>> {
        val language = sharedPreferencesManager.languageTypeTag
        val languageType = LanguageType.fromShowText(language)
        val requestLanguage = convertRepositoryTypeLanguage(languageType)
        return flow {
            emit(getDataFromRepository(requestLanguage, DEFAULT_START_PAGE))
        }.flatMapLatest { initList ->
            loadMoreFlow.scan(initList) { accumulator, _ ->
                val more = getDataFromRepository(requestLanguage, nextPage)
                if (more.isNotEmpty()) {
                    (accumulator + more).distinctBy { it.name }
                } else {
                    accumulator
                }
            }
        }
    }

    override suspend fun loadMore() {
        loadMoreFlow.emit(Unit)
    }

    private suspend fun getDataFromRepository(
        languageType: RepositoryLanguageType,
        page: Int
    ): List<UseCaseAttraction> {
        return taipeiTourAttractionRepository.getData(languageType, page)
            .map {
                it.map { data ->
                    UseCaseAttraction(
                        id = data.id,
                        name = data.name,
                        introduction = data.introduction,
                        openTime = data.openTime,
                        address = data.address,
                        tel = data.tel,
                        email = data.email,
                        lastUpdateTime = data.modified,
                        url = data.url,
                        images = data.images.map { imageData ->
                            imageData.src
                        },
                    )
                }
            }
            .onSuccess {
                nextPage+=1
            }
            .getOrThrow()
    }

    private fun convertRepositoryTypeLanguage(type: LanguageType): RepositoryLanguageType {
        return when (type) {
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