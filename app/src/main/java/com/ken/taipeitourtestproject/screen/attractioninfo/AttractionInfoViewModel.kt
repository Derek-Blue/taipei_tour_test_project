package com.ken.taipeitourtestproject.screen.attractioninfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AttractionInfoViewModel: ViewModel() {

    private val _viewState = MutableStateFlow(AttractionInfoViewState())
    private val currentState get() = _viewState.value
    val viewState = _viewState.asStateFlow()

    fun setCurrentIndex(index: Int) {
        viewModelScope.launch {
            _viewState.update {
                currentState.copy(currentIndex = index)
            }
        }
    }

    fun getCurrentIndex(): Int {
        return currentState.currentIndex
    }
}