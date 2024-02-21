package com.ken.taipeitourtestproject.screen.home.news

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewsShowData(
    val title: String,
    val description: String,
    val url: String,
): Parcelable
