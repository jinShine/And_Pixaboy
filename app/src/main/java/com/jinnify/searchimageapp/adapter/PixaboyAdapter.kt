package com.jinnify.searchimageapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jinnify.searchimageapp.R
import com.jinnify.searchimageapp.data.PixaboyRecyclerType

class PixaboyAdapter(layoutManager: GridLayoutManager) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val FULL_SPAN_SIZE = 3
        const val PIXABOY_ITEM_IMAGE_SIZE = 1

        const val VIEW_TYPE_ITEM_IMAGE = R.layout.viewholder_image
        const val VIEW_TYPE_RESULT_EMPTY = R.layout.viewholder_result_emtpy
    }

    private val itemList = mutableListOf<PixaboyRecyclerType>()

    init {
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return itemList[position].spanSize
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)

        return when (viewType) {
            VIEW_TYPE_ITEM_IMAGE -> ImageViewHolder(view)
            VIEW_TYPE_RESULT_EMPTY -> ResultEmptyViewHolder(view)
            else -> throw RuntimeException("Invalid Type")
        }
    }

    override fun getItemViewType(position: Int) = when (itemList[position]) {
        is PixaboyRecyclerType.ImageItem -> VIEW_TYPE_ITEM_IMAGE
        is PixaboyRecyclerType.ResultEmpty -> VIEW_TYPE_RESULT_EMPTY
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