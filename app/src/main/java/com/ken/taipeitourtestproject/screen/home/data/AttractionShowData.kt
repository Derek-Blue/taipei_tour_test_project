package com.ken.taipeitourtestproject.screen.home.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AttractionShowData(
    val id: Long,
    val name: String,
    val introduction: String,
    val openTime: String,
    val address: String,
    val tel: String,
    val email: String,
    val lastUpdateTime: String,
    val url: String,
    val images: List<String>,
): Parcelable
