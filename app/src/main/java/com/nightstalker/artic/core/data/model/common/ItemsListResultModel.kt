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
    @SerializedName("config")
    val config: Config,
    @SerializedName("data")
    val data: List<T>,
    @SerializedName("info")
    val info: Info,
    @SerializedName("pagination")
    val pagination: Pagination
)