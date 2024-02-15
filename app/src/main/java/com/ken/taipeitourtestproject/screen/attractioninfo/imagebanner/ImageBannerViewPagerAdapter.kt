package com.ken.taipeitourtestproject.screen.attractioninfo.imagebanner

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.viewpager2.adapter.FragmentStateAdapter

class ImageBannerViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return ImageBannerFragment.newInstance(differ.currentList[position])
    }

    fun submitList(newList: List<ImageBannerData>, commitCallback: Runnable? = null) {
        differ.submitList(newList, commitCallback)
    }

    private val differ by lazy {
        AsyncListDiffer(this, ViewPagerDiffCallback())
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