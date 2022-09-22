package com.nightstalker.artic.core.data.model.exhibition


import com.google.gson.annotations.SerializedName
import com.nightstalker.artic.core.data.model.common.Config
import com.nightstalker.artic.core.data.model.common.Info

/**
 * Класс выставки

 * @author Tamerlan Mamukhov
 */
data class ExhibitionModel(
    @SerializedName("config")
    val config: Config,
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("info")
    val info: Info
)