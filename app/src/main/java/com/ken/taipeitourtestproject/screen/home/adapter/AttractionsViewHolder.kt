package com.ken.taipeitourtestproject.screen.home.adapter

import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.ken.taipeitourtestproject.R
import com.ken.taipeitourtestproject.databinding.ItemHomeAttractionBinding
import com.ken.taipeitourtestproject.screen.home.data.AttractionShowData

class AttractionsViewHolder(
    private val binding: ItemHomeAttractionBinding
): BaseAttractionsViewHolder(binding.root) {

    private var onPositionClick: ((Int) -> Unit)? = null

    init {
        itemView.setOnClickListener {
            onPositionClick?.invoke(bindingAdapterPosition)
        }
    }

    override fun bind(data: AttractionShowData, onClickEvent: ((Int) -> Unit)?) {
        this.onPositionClick = onClickEvent
        if (data !is AttractionShowData.Item) return

        val image = data.images.firstOrNull()
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

        binding.titleTextView.text = data.name
        binding.descriptionTextView.text = data.introduction
    }

    override fun recycle() {
        onPositionClick = null
    }
}