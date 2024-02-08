package com.ken.taipeitourtestproject.tools.recyclerview

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

abstract class LoadMoreRecyclerViewScrollListener : RecyclerView.OnScrollListener {

    companion object {
        private const val STARTING_PAGE_INDEX = 0
        private const val BASE_VISIBLE_THRESHOLD = 5
    }

    private val mLayoutManager: RecyclerView.LayoutManager

    private val visibleThreshold: Int

    constructor(layoutManager: LinearLayoutManager, visibleThreshold: Int = BASE_VISIBLE_THRESHOLD) {
        mLayoutManager = layoutManager
        this.visibleThreshold = visibleThreshold
    }

    constructor(layoutManager: GridLayoutManager, visibleThreshold: Int = BASE_VISIBLE_THRESHOLD) {
        mLayoutManager = layoutManager
        this.visibleThreshold = layoutManager.spanCount * visibleThreshold
    }

    constructor(layoutManager: StaggeredGridLayoutManager, visibleThreshold: Int = BASE_VISIBLE_THRESHOLD) {
        mLayoutManager = layoutManager
        this.visibleThreshold = layoutManager.spanCount * visibleThreshold
    }

    private var currentPage = 0

    private var previousTotalItemCount = 0

    private var loading = true

    override fun onScrolled(view: RecyclerView, dx: Int, dy: Int) {
        val totalItemCount = mLayoutManager.itemCount
        val lastVisibleItemPosition = when (mLayoutManager) {
            is StaggeredGridLayoutManager -> {
                val lastVisibleItemPositions = mLayoutManager.findLastVisibleItemPositions(null)
                getLastVisibleItem(lastVisibleItemPositions)
            }
            is GridLayoutManager -> {
                mLayoutManager.findLastVisibleItemPosition()
            }
            is LinearLayoutManager -> {
                mLayoutManager.findLastVisibleItemPosition()
            }
            else -> 0
        }

        if (totalItemCount < previousTotalItemCount) {
            currentPage = STARTING_PAGE_INDEX
            previousTotalItemCount = totalItemCount
            if (totalItemCount == 0) {
                loading = true
            }
        }

        if (loading && totalItemCount > previousTotalItemCount) {
            loading = false
            previousTotalItemCount = totalItemCount
        }

        if (!loading && lastVisibleItemPosition + visibleThreshold > totalItemCount) {
            currentPage++
            onLoadMore(currentPage, totalItemCount, view)
            loading = true
        }
    }

    private fun getLastVisibleItem(lastVisibleItemPositions: IntArray): Int {
        var maxSize = 0
        for (i in lastVisibleItemPositions.indices) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i]
            } else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i]
            }
        }
        return maxSize
    }

    fun resetState() {
        currentPage = STARTING_PAGE_INDEX
        previousTotalItemCount = 0
        loading = true
    }

    abstract fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?)

    //@@FUNCTION
}