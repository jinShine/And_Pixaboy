package com.jinnify.searchimageapp.adapter

import com.jinnify.searchimageapp.adapter.PixaboyAdapter.Companion.FULL_SPAN_SIZE
import com.jinnify.searchimageapp.adapter.PixaboyAdapter.Companion.PIXABOY_ITEM_IMAGE_SIZE

sealed class PixaboyRecyclerType(val spanSize: Int) {

    data class ImageItem(val data: String) : PixaboyRecyclerType(PIXABOY_ITEM_IMAGE_SIZE)

    data class StatusView(val data: Int) : PixaboyRecyclerType(FULL_SPAN_SIZE)

}