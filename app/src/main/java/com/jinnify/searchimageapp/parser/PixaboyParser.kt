package com.jinnify.searchimageapp.parser

import com.jinnify.searchimageapp.repository.PixaboyResponse
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

class PixaboyParser {

    companion object {
        const val BASE_URL = "https://pixabay.com/ko/images/search/"

        const val QUERY_ITEM = "#wrapper #content .media_list div div .flex_grid .item"
        const val QUERY_ITEM_IMG = "a img"
        const val QUERY_ITEM_IMG_SRC = "src"
        const val QUERY_ITEM_PAGINATION_ATTR = "/static/img/blank.gif"
        const val QUERY_ITEM_LAZY_SRC = "data-lazy-srcset"
        const val SPLIT_ATTR = " 1x, "
    }

    private val getItems = { document: Document ->
        document.select(QUERY_ITEM)
    }

    private val getImages = { element: Elements ->
        element.select(QUERY_ITEM_IMG).map {
            if (it.attr(QUERY_ITEM_IMG_SRC) == QUERY_ITEM_PAGINATION_ATTR) {
                it.attr(QUERY_ITEM_LAZY_SRC).split(SPLIT_ATTR).first()
            } else {
                it.attr(QUERY_ITEM_IMG_SRC)
            }
        }
    }

    private fun getDocument(searchWord: String): Document? {

        return try {
            Jsoup.connect("$BASE_URL$searchWord").get()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun searchImageFrom(searchWord: String): PixaboyResponse {

        return getDocument(searchWord)?.let { doc ->
            if (getImages(getItems(doc)).count() > 0) {
                PixaboyResponse.Success(getImages(getItems(doc)))
            } else {
                PixaboyResponse.Failure(ParserError.EMPTY)
            }
        } ?: run {
            PixaboyResponse.Failure(ParserError.NETWORK)
        }
    }

}
