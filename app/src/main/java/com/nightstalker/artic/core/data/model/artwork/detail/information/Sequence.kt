package com.nightstalker.artic.core.data.model.artwork.detail.information


import com.google.gson.annotations.SerializedName

data class Sequence(
    @SerializedName("canvases")
    val canvases: List<Canvase>,
    @SerializedName("@type")
    val type: String
)