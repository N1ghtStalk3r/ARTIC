package com.nightstalker.artic.core.data.model.artwork.detail.information


import com.google.gson.annotations.SerializedName

data class Resource(
    @SerializedName("height")
    val height: Int,
    @SerializedName("@id")
    val id: String,
    @SerializedName("service")
    val service: Service,
    @SerializedName("@type")
    val type: String,
    @SerializedName("width")
    val width: Int
)