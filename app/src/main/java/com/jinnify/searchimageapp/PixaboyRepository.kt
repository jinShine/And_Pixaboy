package com.jinnify.searchimageapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jinnify.searchimageapp.parser.PixaboyParser
import kotlin.concurrent.thread

interface PixaboyRepository {

    fun searchedImageLiveData(): LiveData<List<String>>
    fun fetchSearchImageFrom(text: String)
}

class PixaboyRepositoryImpl(private val pixaboyParser: PixaboyParser) : PixaboyRepository {

    private val pixaboyLiveData = MutableLiveData<List<String>>()

    override fun searchedImageLiveData() = pixaboyLiveData

    override fun fetchSearchImageFrom(text: String) {
        thread {
            Thread.sleep(1000L)
            val searchedImage = pixaboyParser.searchImageFrom(text)
            pixaboyLiveData.postValue(searchedImage)
        }
    }

}