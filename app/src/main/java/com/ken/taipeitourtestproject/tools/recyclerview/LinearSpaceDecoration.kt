package com.kyc.application.module.recyclerview.linearspacedecoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ken.taipeitourtestproject.module.ext.dpToPx

class LinearSpaceDecoration(
    private val topBorderSpace: Int = 0,
    private val itemsSpace: Int = 0,
    private val bottomBorderSpace: Int = 0,
    private val startSpace: Int = 0,
    private val endSpace: Int = 0
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        parent.adapter?.let { adapter ->
            val lastPosition = adapter.itemCount - 1
            val currentPosition = parent.getChildAdapterPosition(view)

            val isLastItem = lastPosition == currentPosition
            when {
                currentPosition == 0 -> {
                    outRect.top = topBorderSpace.dpToPx().toInt()
                    outRect.bottom = itemsSpace.dpToPx().toInt()
                }
                isLastItem -> {
                    outRect.bottom = bottomBorderSpace.dpToPx().toInt()
                }
                else -> {
                    outRect.bottom = itemsSpace.dpToPx().toInt()
                }
            }
            outRect.right = endSpace.dpToPx().toInt()
            outRect.left = startSpace.dpToPx().toInt()
        }
    }

    //@@FUNCTION
}