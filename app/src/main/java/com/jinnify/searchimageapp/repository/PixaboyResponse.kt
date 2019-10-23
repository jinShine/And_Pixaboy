package com.jinnify.searchimageapp.repository

import com.jinnify.searchimageapp.parser.ParserError

sealed class PixaboyResponse {
    data class Failure(val error: ParserError) : PixaboyResponse()
    data class Success(val data: List<String>) : PixaboyResponse()
}