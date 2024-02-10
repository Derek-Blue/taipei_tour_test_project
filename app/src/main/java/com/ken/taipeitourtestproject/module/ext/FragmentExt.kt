package com.ken.taipeitourtestproject.module.ext

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

fun Fragment.navigateScreen(@IdRes resId: Int, bundle: Bundle? = null, isBackStack: Boolean = false) {
    if (isBackStack) { this.finishScreen() }
    this.findNavController().navigate(resId, bundle)
}

fun Fragment.finishScreen() {
    this.findNavController().popBackStack()
}