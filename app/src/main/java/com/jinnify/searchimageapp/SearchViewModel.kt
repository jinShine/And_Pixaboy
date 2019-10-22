package com.jinnify.searchimageapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.jinnify.searchimageapp.repository.PixaboyRepository
import com.jinnify.searchimageapp.repository.PixaboyResponse

class SearchViewModel(private val pixaboyRepository: PixaboyRepository): ViewModel() {

    private val _searchedImageList = MutableLiveData<List<String>>()
    val searchedImageList: LiveData<List<String>> = _searchedImageList

    private val _errorData = MutableLiveData<String>()
    val errorData: LiveData<String> = _errorData

    //Input
    fun searchImageFrom(text: String) {
        pixaboyRepository.fetchSearchImageFrom(text)
    }

    //Output
    private val liveDataManager = Observer<PixaboyResponse> { response ->
        when (response) {
            is PixaboyResponse.Success -> _searchedImageList.value = response.data
            is PixaboyResponse.Failure -> _errorData.value = response.message
        }
    }

    init {
        pixaboyRepository.searchedImageLiveData().observeForever(liveDataManager)
    }

    override fun onCleared() {
        pixaboyRepository.searchedImageLiveData().removeObserver(liveDataManager)
        super.onCleared()
    }
}