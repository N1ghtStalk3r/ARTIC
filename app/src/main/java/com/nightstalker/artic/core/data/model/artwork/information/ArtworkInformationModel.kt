package com.nightstalker.artic.core.data.model.artwork.information


import com.google.gson.annotations.SerializedName

/**
 * Класс описания экспоната
 */
data class ArtworkInformationModel(
    @SerializedName("description")
    val description: List<Description>
)