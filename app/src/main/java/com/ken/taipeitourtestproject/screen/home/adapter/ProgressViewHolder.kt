package com.ken.taipeitourtestproject.screen.home.adapter

import com.ken.taipeitourtestproject.databinding.ItemHomeAttractionProgressBinding
import com.ken.taipeitourtestproject.screen.home.data.AttractionShowData

class ProgressViewHolder(binding: ItemHomeAttractionProgressBinding): BaseAttractionsViewHolder(binding.root) {

    override fun bind(data: AttractionShowData, onClickEvent: ((Int) -> Unit)?) {}

    override fun recycle() {}
}