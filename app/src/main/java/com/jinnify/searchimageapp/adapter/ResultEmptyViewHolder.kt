package com.jinnify.searchimageapp.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.jinnify.searchimageapp.data.PixaboyRecyclerType
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.viewholder_result_emtpy.view.*

class ResultEmptyViewHolder(override val containerView: View) :
    RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(item: PixaboyRecyclerType) {

        if (item !is PixaboyRecyclerType.ResultEmpty) {
            return
        }

        with(containerView) {
            resultDes.text = this.context.getString(item.data)
        }
    }
}