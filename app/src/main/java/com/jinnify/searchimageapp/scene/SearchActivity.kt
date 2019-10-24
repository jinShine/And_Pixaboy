package com.jinnify.searchimageapp.scene

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.jinnify.searchimageapp.R
import com.jinnify.searchimageapp.adapter.PixaboyAdapter
import com.jinnify.searchimageapp.parser.PixaboyParser
import com.jinnify.searchimageapp.repository.PixaboyRepositoryImpl
import com.jinnify.searchimageapp.utility.BaseViewModelFactory
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {

    private var adapter: PixaboyAdapter? = null

    @Suppress("UNCHECKED_CAST")
    private val viewModel: SearchViewModel by lazy {
        ViewModelProviders.of(this, BaseViewModelFactory {
            SearchViewModel(PixaboyRepositoryImpl(PixaboyParser()))
        }).get(SearchViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        setupLayoutManager()
        setupEventBinding()
    }

    private fun searchImage() = searchEditText.text.toString().let { viewModel.searchImageFrom(it) }

    private fun setupLayoutManager() {
        val layoutManager = GridLayoutManager(this, PixaboyAdapter.FULL_SPAN_SIZE)
        adapter = PixaboyAdapter(layoutManager)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }

    private fun setupEventBinding() {

        SearchButton.setOnClickListener { searchImage() }

        swipeRefresh.setOnRefreshListener { searchImage() }

        viewModel.bindingLiveData.observe(this, Observer {
            swipeRefresh.isRefreshing = false
            adapter?.updateAllItems(it)
        })
    }
}