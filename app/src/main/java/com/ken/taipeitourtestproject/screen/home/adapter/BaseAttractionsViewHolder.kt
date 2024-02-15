package com.ken.taipeitourtestproject.screen.home.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ken.taipeitourtestproject.screen.home.data.AttractionShowData

abstract class BaseAttractionsViewHolder(view: View): RecyclerView.ViewHolder(view)  {

    abstract fun bind(data: AttractionShowData, onClickEvent: ((Int) -> Unit)?)

    abstract fun recycle()
}