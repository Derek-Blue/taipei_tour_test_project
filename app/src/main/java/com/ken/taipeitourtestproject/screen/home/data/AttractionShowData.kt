package com.ken.taipeitourtestproject.screen.home.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class AttractionShowData(
    open val id: Long
): Parcelable {

    @Parcelize
    data class Item(
        override val id: Long,
        val name: String,
        val introduction: String,
        val openTime: String,
        val address: String,
        val tel: String,
        val email: String,
        val lastUpdateTime: String,
        val url: String,
        val images: List<String>,
    ): AttractionShowData(id)

    @Parcelize
    data object Progress: AttractionShowData(-9999)
}

