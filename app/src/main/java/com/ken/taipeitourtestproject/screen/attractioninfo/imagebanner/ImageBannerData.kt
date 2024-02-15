package com.ken.taipeitourtestproject.screen.attractioninfo.imagebanner

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageBannerData(
    val index: Int,
    val url: String
): Parcelable
