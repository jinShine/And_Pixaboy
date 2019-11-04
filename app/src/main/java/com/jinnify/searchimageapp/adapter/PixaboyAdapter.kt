package com.jinnify.searchimageapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.RecyclerView
import com.jinnify.searchimageapp.R
import com.jinnify.searchimageapp.adapter.searchviewholder.ImageViewHolder
import com.jinnify.searchimageapp.adapter.searchviewholder.ResultEmptyViewHolder

interface PixaboyEvents {
    fun onItemClick(item: View, itemURL: String)
}

class PixaboyAdapter(
    layoutManager: GridLayoutManager,
    private val events: PixaboyEvents
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val FULL_SPAN_SIZE = 3
        const val PIXABOY_ITEM_IMAGE_SIZE = 1

        const val VIEW_TYPE_ITEM_IMAGE = R.layout.viewholder_image
        const val VIEW_TYPE_STATUS_VIEW = R.layout.viewholder_status
    }

    private val itemList = mutableListOf<PixaboyRecyclerType>()

    init {
        layoutManager.spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int) = itemList[position].spanSize
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)

        return when (viewType) {
            VIEW_TYPE_ITEM_IMAGE -> ImageViewHolder(view, events)
            VIEW_TYPE_STATUS_VIEW -> ResultEmptyViewHolder(view)
            else -> throw RuntimeException("Invalid Type")
        }
    }

    override fun getItemViewType(position: Int) = when (itemList[position]) {
        is PixaboyRecyclerType.ImageItem -> VIEW_TYPE_ITEM_IMAGE
        is PixaboyRecyclerType.StatusView -> VIEW_TYPE_STATUS_VIEW
    }

    override fun getItemCount() = itemList.count()

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ImageViewHolder -> holder.bind(itemList[position])
            is ResultEmptyViewHolder -> holder.bind(itemList[position])
        }
    }

    fun updateAllItems(items: List<PixaboyRecyclerType>) {
        this.itemList.clear()
        this.itemList.addAll(items)

        notifyDataSetChanged()
    }

}
