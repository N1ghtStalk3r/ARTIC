package com.nightstalker.artic.core.data.model.artwork.detail.information


import com.google.gson.annotations.SerializedName

data class Service(
    @SerializedName("@context")
    val context: String,
    @SerializedName("@id")
    val id: String,
    @SerializedName("profile")
    val profile: String
)