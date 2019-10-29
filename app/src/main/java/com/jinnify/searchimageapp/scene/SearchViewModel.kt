package com.jinnify.searchimageapp.scene

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.jinnify.searchimageapp.R
import com.jinnify.searchimageapp.adapter.PixaboyRecyclerType
import com.jinnify.searchimageapp.parser.ParserError
import com.jinnify.searchimageapp.repository.PixaboyRepository
import com.jinnify.searchimageapp.repository.PixaboyResponse

class SearchViewModel(private val pixaboyRepository: PixaboyRepository) : ViewModel() {

    private val _bindingLiveData = MutableLiveData<List<PixaboyRecyclerType>>()
    val bindingLiveData: LiveData<List<PixaboyRecyclerType>> = _bindingLiveData

    private val _isSwipeRefresh = MutableLiveData<Boolean>()
    val isSwipeRefresh: LiveData<Boolean> = _isSwipeRefresh

    //Output
    private val liveDataManager = Observer<PixaboyResponse> { response ->
        when (response) {
            is PixaboyResponse.Success -> {
                _isSwipeRefresh.value = false
                _bindingLiveData.value = transform(response.data)
            }
            is PixaboyResponse.Failure -> {
                _isSwipeRefresh.value = false
                _bindingLiveData.value = when (response.error) {
                    ParserError.EMPTY -> {
                        //-
                        listOf(PixaboyRecyclerType.StatusView(R.string.result_empty))
                    }
                }
            }
        }
    }

    init {
        pixaboyRepository.searchedImageLiveData().observeForever(liveDataManager)
    }

    fun searchImageFrom(searchWord: String) {
        _bindingLiveData.value = listOf(PixaboyRecyclerType.StatusView(R.string.result_loading))
        pixaboyRepository.fetchSearchImageFrom(searchWord)
    }

    override fun onCleared() {
        pixaboyRepository.searchedImageLiveData().removeObserver(liveDataManager)
        super.onCleared()
    }

    private fun transform(items: List<String>) = items.map(PixaboyRecyclerType::ImageItem)

}
