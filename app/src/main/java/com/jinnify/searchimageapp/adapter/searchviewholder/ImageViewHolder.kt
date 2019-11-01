package com.jinnify.searchimageapp.adapter.searchviewholder

import android.view.View
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jinnify.searchimageapp.adapter.PixaboyEvents
import com.jinnify.searchimageapp.adapter.PixaboyRecyclerType
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.viewholder_image.view.*

class ImageViewHolder(
    override val containerView: View,
    private val events: PixaboyEvents
) : RecyclerView.ViewHolder(containerView),
    LayoutContainer {

    companion object {
        const val IMAGE_SIZE = 480
    }

    fun bind(item: PixaboyRecyclerType) {

        if (item !is PixaboyRecyclerType.ImageItem) {
            return
        }

        with(containerView) {

            Glide.with(this)
                .load(item.data)
                .override(IMAGE_SIZE)
                .into(itemImageView)

            itemImageView.setOnClickListener {
                events.onItemClick(this)
            }
        }
    }
}
