package com.nightstalker.artic.core.data.model.exhibition.list

import com.google.gson.annotations.SerializedName
import com.nightstalker.artic.core.data.model.common.Config
import com.nightstalker.artic.core.data.model.common.Info
import com.nightstalker.artic.core.data.model.common.Pagination
import com.nightstalker.artic.core.data.model.exhibition.detail.ExhibitionData

/**
 * Класс со списком данных выставок из сети
 * @property config
 * @property data       список данных
 * @property info
 * @property pagination
 * @constructor Create empty Artworks list result
 */
data class ExhibitionsListResult(
    @SerializedName("config")
    val config: Config,
    @SerializedName("data")
    val data: List<ExhibitionData>,
    @SerializedName("info")
    val info: Info,
    @SerializedName("pagination")
    val pagination: Pagination,
)