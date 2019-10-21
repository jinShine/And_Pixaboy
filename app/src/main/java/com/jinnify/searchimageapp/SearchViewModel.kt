package com.jinnify.searchimageapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel

class SearchViewModel(private val pixaboyRepository: PixaboyRepository): ViewModel() {

    private val _searchedImageList = MutableLiveData<List<String>>()
    val searchedImageList: LiveData<List<String>> = _searchedImageList

    //Input
    fun searchImageFrom(text: String) {
        pixaboyRepository.fetchSearchImageFrom(text)
    }

    //Output
    private val liveDataManager = Observer<List<String>> { imageList ->
        _searchedImageList.value = imageList
    }

    init {
        pixaboyRepository.searchedImageLiveData().observeForever(liveDataManager)
    }

    override fun onCleared() {
        pixaboyRepository.searchedImageLiveData().removeObserver(liveDataManager)
        super.onCleared()
    }
}