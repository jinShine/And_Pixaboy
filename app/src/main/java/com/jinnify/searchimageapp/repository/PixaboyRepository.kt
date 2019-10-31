package com.jinnify.searchimageapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jinnify.searchimageapp.parser.ParserError
import com.jinnify.searchimageapp.parser.PixaboyNetworkException
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

            try {
                val searchedImage = pixaboyParser.searchImageFrom(searchWord)

                if (searchedImage.count() > 0) {
                    pixaboyLiveData.postValue(PixaboyResponse.Success(searchedImage))
                } else {
                    pixaboyLiveData.postValue(PixaboyResponse.Failure(ParserError.EMPTY))
                }
            } catch (e: PixaboyNetworkException) {
                pixaboyLiveData.postValue(PixaboyResponse.Failure(ParserError.NETWORK))
            }
        }
    }
}
