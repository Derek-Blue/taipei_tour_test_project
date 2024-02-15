package com.ken.taipeitourtestproject.base

import android.view.View
import androidx.annotation.ContentView
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import java.util.Calendar

abstract class BaseFragment: Fragment {

    constructor() : super()

    @ContentView
    constructor(@LayoutRes contentLayoutId: Int) : super(contentLayoutId)

    companion object {
        const val CLICK_COUNT_DOWN_TIME = 1000L
    }

    private var clickEventTimeStep: Long = 0

    protected fun isClickSafe(): Boolean {
        val now = Calendar.getInstance().timeInMillis
        return when {
            now - clickEventTimeStep > CLICK_COUNT_DOWN_TIME -> {
                clickEventTimeStep = now
                true
            }
            else -> {
                false
            }
        }
    }

    //防止手震連點事件
    protected fun View.setOnClickDebounce(callback: () -> Unit) {
        setOnClickListener {
            if (isClickSafe()) {
                callback.invoke()
            }
        }
    }
}