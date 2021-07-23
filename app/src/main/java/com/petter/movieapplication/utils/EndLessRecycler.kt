package com.petter.movieapplication.utils

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

abstract class EndLessRecycler : RecyclerView.OnScrollListener() {

    abstract fun onLoadNextPage()

    private var layoutManagerType: LayoutManagerType = LayoutManagerType.LinearLayout
    private var lastVisibleItemPosition: Int = 0
    private var currentScrollState: Int = 0

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val layoutManager = recyclerView.layoutManager
        layoutManagerType = associateLayoutManagerToType(layoutManager)
        lastVisibleItemPosition = getLastVisibleItemPosition(layoutManager)
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        currentScrollState = newState
        val layoutManager = recyclerView.layoutManager
        layoutManager?.let {
            val visibleItemCount = it.childCount
            val totalItemCount = it.itemCount
            if (visibleItemCount > 0 && currentScrollState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItemPosition >= totalItemCount - 1)
                onLoadNextPage()
        }
    }

    private fun associateLayoutManagerToType(layoutManager: RecyclerView.LayoutManager?): LayoutManagerType {
        return when (layoutManager) {
            is LinearLayoutManager -> LayoutManagerType.LinearLayout
            is StaggeredGridLayoutManager -> LayoutManagerType.StaggeredGridLayout
            is GridLayoutManager -> LayoutManagerType.GridLayout
            else -> throw RuntimeException("Unsupported LayoutManager used.")
        }
    }

    private fun getLastVisibleItemPosition(layoutManager: RecyclerView.LayoutManager?): Int {
        return when (layoutManagerType) {
            LayoutManagerType.LinearLayout ->
                (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
            LayoutManagerType.GridLayout ->
                (layoutManager as GridLayoutManager).findLastVisibleItemPosition()
            else -> (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
        }
    }
}

enum class LayoutManagerType {
    LinearLayout,
    StaggeredGridLayout,
    GridLayout
}