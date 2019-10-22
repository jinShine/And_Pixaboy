package com.jinnify.searchimageapp.repository

sealed class PixaboyResponse {
    data class Failure(val message: String) : PixaboyResponse()
    data class Success(val data: List<String>) : PixaboyResponse()
}