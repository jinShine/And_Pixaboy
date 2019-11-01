package com.jinnify.searchimageapp.scene.search

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.jinnify.searchimageapp.R
import com.jinnify.searchimageapp.adapter.PixaboyAdapter
import com.jinnify.searchimageapp.adapter.PixaboyEvents
import com.jinnify.searchimageapp.parser.PixaboyParser
import com.jinnify.searchimageapp.repository.PixaboyRepositoryImpl
import com.jinnify.searchimageapp.scene.detail.DetailActivity
import com.jinnify.searchimageapp.utility.BaseViewModelFactory
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity(), PixaboyEvents {

    private var adapter: PixaboyAdapter? = null
    private var searchWord: String? = null

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
                searchWord = query
                searchWord?.let { viewModel.searchImageFrom(it) }
                searchView.clearFocus()

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onItemClick(item: View) {
        println("")
//        val anim = AnimationUtils.loadAnimation(this, R.anim.anim_translate_scale)
//        item.startAnimation(anim)

//        item.bringToFront()
//        item.animate()
//            .translationX(130.0f)
//            .translationY(130.0f)
//            .scaleX(2.0f)
//            .scaleY(2.0f)
//            .setDuration(1000L)
//            .start()
        startActivity(Intent(this, DetailActivity::class.java))

    }

    private fun setupLayoutManager() {
        val layoutManager = GridLayoutManager(this, PixaboyAdapter.FULL_SPAN_SIZE)
        adapter = PixaboyAdapter(layoutManager, this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }

    private fun setupEventBinding() {

        swipeRefresh.setOnRefreshListener { searchWord?.let(viewModel::searchImageFrom) }

        viewModel.isSwipeRefresh.observe(this, Observer {
            swipeRefresh.isRefreshing = it
        })

        viewModel.bindingLiveData.observe(this, Observer {
            adapter?.updateAllItems(it)
        })
    }
}
