package com.nightstalker.artic.features.artwork.presentation.ui.dialog

import com.nightstalker.artic.network.ApiConstants.ARTWORK_TYPE_TITLE
import com.nightstalker.artic.network.ApiConstants.PLACE_OF_ORIGIN

/**
 *
 * Конструктор забросов в сеть для поиска экспонатов
 * @author Tamerlan Mamukhov
 * @created 2022-11-15
 */
object SearchArtworksQueryConstructor {
    fun create(
        searchQuery: String,
        place: String = "",
        type: String = "",
    ): String {
        val params = StringBuilder()
        params.apply {
            append(LEFT_CURLY_BRACE)
            if (searchQuery.isNotEmpty()) {
                append(createQueryPath(searchQuery))
                append(COMMA)
            }
            append(QUERY_BOOL_SHOULD)
            append(LEFT_BRACE)
            if (place.isNotEmpty()) {
                append(createQueryMatchPart(PLACE_OF_ORIGIN, place))
            }
            if (type.isNotEmpty()) {
                append(COMMA)
                append(createQueryMatchPart(ARTWORK_TYPE_TITLE, type))
            }
            append(RIGHT_BRACE)
            append(THREE_RIGHT_CURLY_BRACES)
        }
        return params.toString()
    }

    private fun createQueryPath(searchQuery: String) = "\"q\":\"$searchQuery\""

    private fun createQueryMatchPart(field: String, value: String) = "{ \"match\": { \"$field\": \"$value\" } }"

    private const val QUERY_BOOL_SHOULD = "\"query\": {\"bool\": {\"should\":"

    private const val COMMA = ","

    private const val LEFT_BRACE = "["
    private const val RIGHT_BRACE = "]"

    private const val LEFT_CURLY_BRACE = "{"
    private const val RIGHT_CURLY_BRACE = "}"

    private const val THREE_RIGHT_CURLY_BRACES = "}}}"
}