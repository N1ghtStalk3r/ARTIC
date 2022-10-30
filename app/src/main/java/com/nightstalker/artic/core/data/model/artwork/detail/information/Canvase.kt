package com.nightstalker.artic.core.data.model.artwork.detail.information


import com.google.gson.annotations.SerializedName

data class Canvase(
    @SerializedName("height")
    val height: Int,
    @SerializedName("@id")
    val id: String,
    @SerializedName("images")
    val images: List<Image>,
    @SerializedName("label")
    val label: String,
    @SerializedName("@type")
    val type: String,
    @SerializedName("width")
    val width: Int
)