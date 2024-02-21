package com.ken.taipeitourtestproject.screen.home

import com.ken.taipeitourtestproject.screen.home.data.AttractionShowData
import com.ken.taipeitourtestproject.screen.home.news.NewsShowData

data class HomeViewState(
    val isProgress: Boolean = false,
    val newsShowData: List<NewsShowData> = emptyList(),
    val attractionList: List<AttractionShowData.Item> = emptyList(),
    val showItems: List<AttractionShowData> = emptyList(),
)
