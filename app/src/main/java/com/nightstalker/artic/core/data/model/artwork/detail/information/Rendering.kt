package com.nightstalker.artic.core.data.model.artwork.detail.information


import com.google.gson.annotations.SerializedName

data class Rendering(
    @SerializedName("format")
    val format: String,
    @SerializedName("@id")
    val id: String,
    @SerializedName("label")
    val label: String
)