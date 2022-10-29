package com.nightstalker.artic.core.data.model.artwork.detail.information


import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("motivation")
    val motivation: String,
    @SerializedName("on")
    val on: String,
    @SerializedName("resource")
    val resource: Resource,
    @SerializedName("@type")
    val type: String
)