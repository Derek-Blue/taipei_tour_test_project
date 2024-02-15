package com.ken.taipeitourtestproject.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ken.taipeitourtestproject.screen.home.data.AttractionShowData
import com.ken.taipeitourtestproject.usecase.attractionlist.AttractionListUseCase
import com.ken.taipeitourtestproject.usecase.attractionlist.UseCaseAttraction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val attractionListUseCase: AttractionListUseCase
): ViewModel() {

    private val _viewState = MutableStateFlow(HomeViewState())
    private val currentState get() = _viewState.value
    val viewState = _viewState.asStateFlow()

    init {
        viewModelScope.launch {
            flowOf(Unit).onStart {
                _viewState.update {
                    currentState.copy(isProgress = true)
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