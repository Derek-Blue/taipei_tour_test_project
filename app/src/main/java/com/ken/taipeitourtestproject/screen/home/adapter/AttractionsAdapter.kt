package com.ken.taipeitourtestproject.screen.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.ken.taipeitourtestproject.R
import com.ken.taipeitourtestproject.databinding.ItemHomeAttractionBinding
import com.ken.taipeitourtestproject.databinding.ItemHomeAttractionProgressBinding
import com.ken.taipeitourtestproject.screen.home.data.AttractionShowData

class AttractionsAdapter(
    private val onItemClick: (AttractionShowData) -> Unit
): ListAdapter<AttractionShowData, BaseAttractionsViewHolder>(
    object : DiffUtil.ItemCallback<AttractionShowData>(){
        override fun areItemsTheSame(oldItem: AttractionShowData, newItem: AttractionShowData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: AttractionShowData, newItem: AttractionShowData): Boolean {
            return oldItem == newItem
        }
    }
) {

    private val onClickEvent = { position: Int ->
        if (position in currentList.indices) {
            onItemClick.invoke(currentList[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseAttractionsViewHolder {
        return if (viewType == 0) {
            AttractionsViewHolder(
                ItemHomeAttractionBinding.bind(
                    LayoutInflater.from(parent.context).inflate(R.layout.item_home_attraction, parent, false)
                )
            )
        } else {
            ProgressViewHolder(
                ItemHomeAttractionProgressBinding.bind(
                    LayoutInflater.from(parent.context).inflate(R.layout.item_home_attraction_progress, parent, false)
                )
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (currentList[position] is AttractionShowData.Item) 0 else 1
    }

    override fun onBindViewHolder(holder: BaseAttractionsViewHolder, position: Int) {
        holder.bind(currentList[position], onClickEvent)
    }

    override fun onViewRecycled(holder: BaseAttractionsViewHolder) {
        holder.recycle()
        super.onViewRecycled(holder)
    }
}