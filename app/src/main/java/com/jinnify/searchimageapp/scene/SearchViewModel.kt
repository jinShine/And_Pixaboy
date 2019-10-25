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

    //Output
    private val liveDataManager = Observer<PixaboyResponse> { response ->
        when (response) {
            is PixaboyResponse.Success -> _bindingLiveData.value = transform(response.data)
            is PixaboyResponse.Failure -> _bindingLiveData.value = when (response.error) {
                ParserError.EMPTY -> {
                    //-
                    listOf(PixaboyRecyclerType.StatusView(R.string.result_empty))
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

    //-
//    private
//    when (response) {
//        is PixaboyResponse.Success -> _bindingLiveData.value = transform(response.data)
//        is PixaboyResponse.Failure -> _bindingLiveData.value = when (response.error) {
//            ParserError.EMPTY -> {
//            listOf(PixaboyRecyclerType.StatusView(R.string.result_empty))
//        }
//        }
//    }
    private fun transform(items: List<String>) = items.map(PixaboyRecyclerType::ImageItem)




}