package com.ken.taipeitourtestproject.usecase.attractionlist

import com.ken.taipeitourtestproject.module.repository.attractions.TaipeiTourAttractionRepository
import com.ken.taipeitourtestproject.module.repository.attractions.request.RepositoryLanguageType
import com.ken.taipeitourtestproject.module.repository.language.LanguageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.scan

class AttractionListUseCaseImpl(
    private val languageRepository: LanguageRepository,
    private val taipeiTourAttractionRepository: TaipeiTourAttractionRepository
): AttractionListUseCase {

    companion object {
        private const val DEFAULT_START_PAGE = 1
    }

    //cache data by Language
    private val cacheMap: MutableMap<RepositoryLanguageType, List<UseCaseAttraction>> = mutableMapOf()
    //record load more index page
    private val nextPageMap: MutableMap<RepositoryLanguageType, Int> = mutableMapOf()

    private val loadMoreFlow = MutableSharedFlow<Unit>()

    override suspend fun subscribe(): Flow<List<UseCaseAttraction>> {
        return languageRepository.getFlow().map {
            val cacheList = cacheMap[it]
            val newList = if (cacheList.isNullOrEmpty()) {
                getDataFromRepository(it, DEFAULT_START_PAGE)
            } else {
                cacheList
            }
            UseCaseMediator(it, newList)
        }.flatMapLatest { md ->
            loadMoreFlow.scan(md.content) { accumulator, _ ->
                val languageType = md.languageType
                val nextPage = nextPageMap[languageType] ?: DEFAULT_START_PAGE
                val more = getDataFromRepository(languageType, nextPage)
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
            .onSuccess { result ->
                val cache = cacheMap[languageType] ?: emptyList()
                val newData = (cache + result).distinctBy { it.name }
                cacheMap[languageType] = newData
                nextPageMap[languageType] = page + 1
            }
            .getOrThrow()
    }

    private data class UseCaseMediator(
        val languageType: RepositoryLanguageType,
        val content: List<UseCaseAttraction>
    )
}