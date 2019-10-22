package com.jinnify.searchimageapp.repository

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jinnify.searchimageapp.R
import com.jinnify.searchimageapp.parser.PixaboyParser
import kotlin.concurrent.thread

interface PixaboyRepository {

    fun searchedImageLiveData(): LiveData<PixaboyResponse>
    fun fetchSearchImageFrom(text: String)
}

class PixaboyRepositoryImpl(private val pixaboyParser: PixaboyParser) :
    PixaboyRepository {

    private val pixaboyLiveData = MutableLiveData<PixaboyResponse>()

    override fun searchedImageLiveData() = pixaboyLiveData

    override fun fetchSearchImageFrom(text: String) {
        thread {
            Thread.sleep(1000L)
            val searchedImage = pixaboyParser.searchImageFrom(text)

            if (searchedImage.count() > 0) {
                pixaboyLiveData.postValue(PixaboyResponse.Success(searchedImage))
            } else {
                pixaboyLiveData.postValue(
                    PixaboyResponse.Failure(Resources.getSystem().getString(R.string.response_emtpy))
                )
            }

            println("$searchedImage")
        }
    }
}