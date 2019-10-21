package com.jinnify.searchimageapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.jinnify.searchimageapp.parser.PixaboyParser
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        @Suppress("UNCHECKED_CAST")
        val viewModel = ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return SearchViewModel(PixaboyRepositoryImpl(PixaboyParser())) as T
            }
        }).get(SearchViewModel::class.java)

        SearchButton.setOnClickListener {
            println("")
            val searchText = searchEditText.text.toString()
            viewModel.searchImageFrom(searchText)
        }

        viewModel.searchedImageList.observe(this, Observer {
            println("" + it)
        })

    }
}