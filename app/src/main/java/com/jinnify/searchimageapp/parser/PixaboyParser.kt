package com.jinnify.searchimageapp.parser

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

class PixaboyParser {

    companion object {
        const val BASE_URL = "https://pixabay.com/ko/images/search/"
        const val QUERY_ITEM = "#wrapper #content .media_list div div .flex_grid .item"
        const val QUERY_ITEM_IMG = "a img"
        const val QUERY_ITEM_IMG_SRC = "srcset"
    }

    private val getItems = { document: Document ->
        document.select(QUERY_ITEM)
    }

    private val getImages = { element: Elements ->
        element.select(QUERY_ITEM_IMG).let { el ->
            el.map { it.attr(QUERY_ITEM_IMG_SRC).split(" 1x, ").first() }
        }
    }

    private val getDocument = { text: String ->
        Jsoup.connect(BASE_URL + text).get()
    }

    private fun searchImageComposition(
        getDocument: (String) -> Document,
        getItems: (Document) -> Elements,
        getImages: (Elements) -> List<String>
    ): (String) -> List<String> {

        return { searchText ->
            getImages(getItems(getDocument(searchText)))
        }
    }

    fun searchImageFrom(text: String): List<String> =
        searchImageComposition(getDocument, getItems, getImages)(text)

}