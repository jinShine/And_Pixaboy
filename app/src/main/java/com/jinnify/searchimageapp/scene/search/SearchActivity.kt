package com.jinnify.searchimageapp.scene.search

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.jinnify.searchimageapp.R
import com.jinnify.searchimageapp.adapter.PixaboyAdapter
import com.jinnify.searchimageapp.adapter.PixaboyEvents
import com.jinnify.searchimageapp.core.Constant
import com.jinnify.searchimageapp.parser.PixaboyParser
import com.jinnify.searchimageapp.repository.PixaboyRepositoryImpl
import com.jinnify.searchimageapp.scene.detail.DetailActivity
import com.jinnify.searchimageapp.utility.BaseViewModelFactory
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.viewholder_image.view.*

class SearchActivity : AppCompatActivity(), PixaboyEvents {

    private var adapter: PixaboyAdapter? = null

    @Suppress("UNCHECKED_CAST")
    private val viewModel: SearchViewModel by lazy {
        ViewModelProviders.of(this, BaseViewModelFactory {
            SearchViewModel(
                PixaboyRepositoryImpl(
                    PixaboyParser()
                )
            )
        }).get(SearchViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        setupLayoutManager()
        setupEventBinding()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.search_menu, menu)
        val searchView = menu?.findItem(R.id.actionSearch)?.actionView as SearchView
        searchView.queryHint = getString(R.string.search_hint)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.searchImageFrom(query)
                searchView.clearFocus()

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onItemClick(item: View, itemURL: String) =
        startActivity(
            Intent(this, DetailActivity::class.java).apply {
                putExtra(Constant.Intent.SELECTED_IMAGE_URL, itemURL)
            },
            ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,
                item.itemImageView,
                item.itemImageView.transitionName
            ).toBundle()
        )

    private fun setupLayoutManager() {
        val layoutManager = GridLayoutManager(this, PixaboyAdapter.FULL_SPAN_SIZE)
        adapter = PixaboyAdapter(layoutManager, this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }

    private fun setupEventBinding() {

        swipeRefresh.setOnRefreshListener { viewModel.searchImageFrom() }

        viewModel.isSwipeRefresh.observe(this, Observer {
            swipeRefresh.isRefreshing = it
        })

        viewModel.bindingLiveData.observe(this, Observer {
            adapter?.updateAllItems(it)
        })
    }
}
