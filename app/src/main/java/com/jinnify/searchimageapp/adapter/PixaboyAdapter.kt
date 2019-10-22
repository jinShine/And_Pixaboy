package com.jinnify.searchimageapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jinnify.searchimageapp.R

class PixaboyAdapter(layoutManager: GridLayoutManager) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_ITEM_IMAGE = R.layout.viewholder_image
    }

    private val itemList = mutableListOf<String>()

    init {
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return 1
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(VIEW_TYPE_ITEM_IMAGE, parent, false)

        return ImageViewHolder(view)
    }

    override fun getItemCount() = itemList.count()

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ImageViewHolder -> holder.bind(itemList[position])
        }
    }

    fun updateAllItems(items: List<String>) {
        this.itemList.clear()
        this.itemList.addAll(items)

        notifyDataSetChanged()
    }
}