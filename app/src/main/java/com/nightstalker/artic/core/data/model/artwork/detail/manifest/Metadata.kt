package com.nightstalker.artic.core.data.model.artwork.detail.manifest


import com.google.gson.annotations.SerializedName

data class Metadata(
    @SerializedName("label")
    val label: String,
    @SerializedName("value")
    val value: String
)