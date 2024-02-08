package com.ken.taipeitourtestproject.screen.home

import com.ken.taipeitourtestproject.screen.home.data.AttractionShowData

data class HomeViewState(
    val isProgress: Boolean = false,
    val attractionList: List<AttractionShowData> = emptyList()
)
