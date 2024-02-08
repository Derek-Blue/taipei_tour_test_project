package com.ken.taipeitourtestproject.screen.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.ken.taipeitourtestproject.R
import com.ken.taipeitourtestproject.databinding.ItemHomeAttractionBinding
import com.ken.taipeitourtestproject.screen.home.data.AttractionShowData

class AttractionsAdapter(
    private val onItemClick: (AttractionShowData) -> Unit
): ListAdapter<AttractionShowData, AttractionsViewHolder>(
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttractionsViewHolder {
        return AttractionsViewHolder(
            ItemHomeAttractionBinding.bind(
                LayoutInflater.from(parent.context).inflate(R.layout.item_home_attraction, parent, false)
            )
        )
    }

    override fun onBindViewHolder(holder: AttractionsViewHolder, position: Int) {
        holder.bind(currentList[position], onClickEvent)
    }

    override fun onViewRecycled(holder: AttractionsViewHolder) {
        holder.recycler()
        super.onViewRecycled(holder)
    }
}