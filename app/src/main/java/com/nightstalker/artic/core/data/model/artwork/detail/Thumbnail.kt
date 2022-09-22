package com.nightstalker.artic.core.data.model.artwork.detail


import com.google.gson.annotations.SerializedName

data class Thumbnail(
    @SerializedName("alt_text")
    val altText: String,
    @SerializedName("height")
    val height: Int,
    @SerializedName("lqip")
    val lqip: String,
    @SerializedName("width")
    val width: Int
)