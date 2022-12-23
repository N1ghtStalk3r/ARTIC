package com.nightstalker.artic.core.data.model.common

import com.google.gson.annotations.SerializedName

/**
 * Класс результатов запроса в сеть
 *
 * @param T                 тип данных
 * @property config         конфиг
 * @property data           данные
 * @property info           информация
 * @property pagination     пагинация
 * @author Tamerlan Mamukhov
 */
data class ItemsListResultModel<T>(
    @SerializedName("data")
    val data: List<T>,
    @SerializedName("pagination")
    val pagination: Pagination
)