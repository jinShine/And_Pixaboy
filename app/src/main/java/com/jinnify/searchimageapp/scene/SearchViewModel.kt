package com.jinnify.searchimageapp.scene

import androidx.lifecycle.*
import com.jinnify.searchimageapp.R
import com.jinnify.searchimageapp.data.PixaboyRecyclerType
import com.jinnify.searchimageapp.parser.ParserError
import com.jinnify.searchimageapp.repository.PixaboyRepository
import com.jinnify.searchimageapp.repository.PixaboyResponse

class SearchViewModel(private val pixaboyRepository: PixaboyRepository) : ViewModel() {

    private val _searchedImageList = MutableLiveData<List<PixaboyRecyclerType>>()
    val searchedImageList: LiveData<List<PixaboyRecyclerType>> = _searchedImageList

    //Input
    fun searchImageFrom(searchWord: String) = pixaboyRepository.fetchSearchImageFrom(searchWord)

    //Output
    private val liveDataManager = Observer<PixaboyResponse> { response ->
        when (response) {
            is PixaboyResponse.Success -> _searchedImageList.value = transform(response.data)
            is PixaboyResponse.Failure -> _searchedImageList.value = when (response.error) {
                ParserError.EMPTY -> {
                    listOf(PixaboyRecyclerType.ResultEmpty(R.string.result_empty))
                }
            }
        }
    }

    init {
        pixaboyRepository.searchedImageLiveData().observeForever(liveDataManager)
    }

    private fun transform(items: List<String>) = mutableListOf<PixaboyRecyclerType>()
        .apply { items.forEach { add(PixaboyRecyclerType.ImageItem(it)) } }

    override fun onCleared() {
        pixaboyRepository.searchedImageLiveData().removeObserver(liveDataManager)
        super.onCleared()
    }
}