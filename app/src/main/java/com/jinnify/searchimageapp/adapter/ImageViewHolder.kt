package com.jinnify.searchimageapp.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.viewholder_image.view.*

class ImageViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
    LayoutContainer {

    companion object {
        const val IMAGE_SIZE = 480
    }

    fun bind(imageUrl: String) {

        with(containerView) {
            Glide.with(this)
                .load(imageUrl)
                .override(IMAGE_SIZE)
                .into(itemImageView)
        }
    }
}