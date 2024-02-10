package com.ken.taipeitourtestproject.screen.attractioninfo.imagebanner

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.viewpager2.adapter.FragmentStateAdapter

class ImageBannerViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemId(position: Int): Long {
        val realPosition = position % getRealCount()
        return differ.currentList[realPosition].hashCode().toLong()
    }

    override fun containsItem(itemId: Long): Boolean {
        return differ.currentList.any { it.hashCode().toLong() == itemId }
    }

    override fun getItemCount(): Int {
        return if (getRealCount() <= 1) {
            getRealCount()
        } else {
            Int.MAX_VALUE
        }
    }

    fun getRealCount(): Int {
        return differ.currentList.size
    }

    override fun createFragment(position: Int): Fragment {
        val realPosition = position % getRealCount()
        return ImageBannerFragment.newInstance(differ.currentList[realPosition])
    }

    private val differ by lazy {
        AsyncListDiffer(this, ViewPagerDiffCallback())
    }

    fun submitList(newList: List<ImageBannerData>, commitCallback: Runnable? = null) {
        differ.submitList(newList, commitCallback)
    }

    private class ViewPagerDiffCallback : DiffUtil.ItemCallback<ImageBannerData>() {
        override fun areItemsTheSame(oldItem: ImageBannerData, newItem: ImageBannerData): Boolean {
            return oldItem.index == newItem.index
        }

        override fun areContentsTheSame(oldItem: ImageBannerData, newItem: ImageBannerData): Boolean {
            return oldItem == newItem
        }
    }
}