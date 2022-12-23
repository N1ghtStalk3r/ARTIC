package com.nightstalker.artic.core.data.model.artwork.information


import com.google.gson.annotations.SerializedName

data class Description(
    @SerializedName("language")
    val language: String,
    @SerializedName("value")
    val value: String
)