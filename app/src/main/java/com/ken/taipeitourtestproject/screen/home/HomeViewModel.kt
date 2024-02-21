package com.ken.taipeitourtestproject.screen.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ken.taipeitourtestproject.screen.home.data.AttractionShowData
import com.ken.taipeitourtestproject.screen.home.news.NewsShowData
import com.ken.taipeitourtestproject.usecase.attractionlist.AttractionListUseCase
import com.ken.taipeitourtestproject.usecase.attractionlist.UseCaseAttraction
import com.ken.taipeitourtestproject.usecase.news.NewsUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val attractionListUseCase: AttractionListUseCase,
    private val newsUseCase: NewsUseCase
): ViewModel() {

    private val _viewState = MutableStateFlow(HomeViewState())
    private val currentState get() = _viewState.value
    val viewState = _viewState.asStateFlow()

    private val _showUrlFlow = MutableSharedFlow<String>()
    val showUrlFlow = _showUrlFlow.asSharedFlow()

    init {
        viewModelScope.launch {
            flow {
                val news = newsUseCase.getHomePageNews().getOrThrow()
                val initNews = news.map {
                    NewsShowData(it.title, it.description, it.url)
                }
                emit(initNews)
            }.onStart {
                _viewState.update {
                    currentState.copy(isProgress = true)
                }
            }.onEach { initNews ->
                _viewState.update {
                    currentState.copy(newsShowData = initNews)
                }
            }.flatMapLatest {
                attractionListUseCase.subscribe()
                    .onEach {
                        _viewState.update {
                            currentState.copy(
                                showItems = currentState.attractionList,
                                isProgress = false
                            )
                        }
                    }
                    .map { useCase ->
                        convertShowData(useCase)
                    }
            }.catch {
                it.stackTrace
            }.collectLatest { newList ->
                _viewState.update {
                    currentState.copy(
                        attractionList = newList,
                        showItems = newList
                    )
                }
            }
        }
    }

    fun onLoadMore() {
        viewModelScope.launch {
            _viewState.update {
                val showItems = if (currentState.attractionList.isEmpty()) {
                    emptyList()
                } else {
                    currentState.attractionList + listOf(AttractionShowData.Progress)
                }
                currentState.copy(
                    isProgress = true,
                    showItems = showItems
                )
            }
            attractionListUseCase.loadMore()
        }
    }

    fun showNewsWebView(url: String) {
        viewModelScope.launch {
            _showUrlFlow.emit(url)
        }
    }

    private fun convertShowData(listUseCase: List<UseCaseAttraction>): List<AttractionShowData.Item> {
        return listUseCase.map {
            AttractionShowData.Item(
                it.id,
                it.name,
                it.introduction,
                it.openTime,
                it.address,
                it.tel,
                it.email,
                it.lastUpdateTime,
                it.url,
                it.images
            )
        }
    }
}