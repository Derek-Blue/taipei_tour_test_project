package com.ken.taipeitourtestproject.screen.home.news

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.viewpager2.adapter.FragmentStateAdapter

class NewsBannerAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return NewsBannerFragment.newInstance(differ.currentList[position])
    }

    override fun getItemId(position: Int): Long {
        return differ.currentList[position].hashCode().toLong()
    }

    private val differ by lazy {
        AsyncListDiffer(this, VideoBannerDiffCallback())
    }

    fun submitList(newList: List<NewsShowData>?, commitCallback: Runnable? = null) {
        differ.submitList(newList, commitCallback)
    }

    private class VideoBannerDiffCallback : DiffUtil.ItemCallback<NewsShowData>() {
        override fun areItemsTheSame(oldItem: NewsShowData, newItem: NewsShowData): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: NewsShowData, newItem: NewsShowData): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
}