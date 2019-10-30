package com.jinnify.searchimageapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jinnify.searchimageapp.parser.PixaboyParser
import kotlin.concurrent.thread

interface PixaboyRepository {

    fun searchedImageLiveData(): LiveData<PixaboyResponse>
    fun fetchSearchImageFrom(searchWord: String)
}

class PixaboyRepositoryImpl(private val pixaboyParser: PixaboyParser) : PixaboyRepository {

    private val pixaboyLiveData = MutableLiveData<PixaboyResponse>()

    override fun searchedImageLiveData() = pixaboyLiveData

    override fun fetchSearchImageFrom(searchWord: String) {
        thread {
            Thread.sleep(1000L)
            pixaboyLiveData.postValue(pixaboyParser.searchImageFrom(searchWord))
        }
    }
}
