package com.jinnify.searchimageapp.Scene

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.jinnify.searchimageapp.R
import com.jinnify.searchimageapp.adapter.PixaboyAdapter
import com.jinnify.searchimageapp.parser.PixaboyParser
import com.jinnify.searchimageapp.repository.PixaboyRepositoryImpl
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {

    private var adapter: PixaboyAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setupLayoutManager()

        @Suppress("UNCHECKED_CAST")
        val viewModel = ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return SearchViewModel(
                    PixaboyRepositoryImpl(
                        PixaboyParser()
                    )
                ) as T
            }
        }).get(SearchViewModel::class.java)

        SearchButton.setOnClickListener {
            val searchText = searchEditText.text.toString()
            viewModel.searchImageFrom(searchText)
        }

        swipeRefresh.setOnRefreshListener {
            val searchText = searchEditText.text.toString()
            viewModel.searchImageFrom(searchText)
        }

        viewModel.searchedImageList.observe(this, Observer {
            swipeRefresh.isRefreshing = false
            adapter?.updateAllItems(it)
        })

    }

    private fun setupLayoutManager() {
        val layoutManager = GridLayoutManager(this, PixaboyAdapter.FULL_SPAN_SIZE)
        adapter = PixaboyAdapter(layoutManager)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }
}