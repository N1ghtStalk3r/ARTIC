package com.nightstalker.artic.core.data.model.common

import com.google.gson.annotations.SerializedName

/**
 * Класс результатов запроса в сеть
 *
 * @param T                 тип данных
 * @property data           данные
 * @property pagination     пагинация
 * @author Tamerlan Mamukhov
 */
data class SearchResultsModel2<T>(
    @SerializedName("data")
    val data: T,
    @SerializedName("pagination")
    val pagination: Pagination,
)