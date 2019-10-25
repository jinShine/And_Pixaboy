package com.jinnify.searchimageapp.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.viewholder_status.view.*

class ResultEmptyViewHolder(override val containerView: View) :
    RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(item: PixaboyRecyclerType) {

        if (item !is PixaboyRecyclerType.StatusView) {
            return
        }

        with(containerView) {
            resultDes.text = this.context.getString(item.data)
        }
    }
}