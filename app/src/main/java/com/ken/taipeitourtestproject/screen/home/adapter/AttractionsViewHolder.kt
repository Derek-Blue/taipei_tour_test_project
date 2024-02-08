package com.ken.taipeitourtestproject.screen.home.adapter

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ken.taipeitourtestproject.R
import com.ken.taipeitourtestproject.databinding.ItemHomeAttractionBinding
import com.ken.taipeitourtestproject.screen.home.data.AttractionShowData

class AttractionsViewHolder(
    private val binding: ItemHomeAttractionBinding
): RecyclerView.ViewHolder(binding.root) {

    private var onPositionClick: ((Int) -> Unit)? = null

    init {
        itemView.setOnClickListener {
            onPositionClick?.invoke(bindingAdapterPosition)
        }
    }

    fun bind(item: AttractionShowData, onClickEvent: (Int) -> Unit) {
        this.onPositionClick = onClickEvent

        val image = item.images.firstOrNull()
        if (image.isNullOrBlank()) {
            binding.emptyImageConstraintLayout.isVisible = true
            binding.iconImageView.isVisible = false
        } else {
            binding.emptyImageConstraintLayout.isVisible = false
            binding.iconImageView.isVisible = true
            Glide.with(itemView)
                .load(image)
                .thumbnail(Glide.with(itemView).load(R.raw.progress_image))
                .into(binding.iconImageView)
        }

        binding.titleTextView.text = item.name
        binding.descriptionTextView.text = item.introduction
    }

    fun recycler() {
        onPositionClick = null
    }
}