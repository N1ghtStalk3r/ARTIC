package com.nightstalker.artic.core.data.model.artwork.detail.information


import com.google.gson.annotations.SerializedName

data class ArtworkInformationModel(
    @SerializedName("attribution")
    val attribution: String,
    @SerializedName("@context")
    val context: String,
    @SerializedName("description")
    val description: List<Description>,
    @SerializedName("@id")
    val id: String,
    @SerializedName("label")
    val label: String,
    @SerializedName("logo")
    val logo: String,
    @SerializedName("metadata")
    val metadata: List<Metadata>,
    @SerializedName("rendering")
    val rendering: Rendering,
    @SerializedName("sequences")
    val sequences: List<Sequence>,
    @SerializedName("@type")
    val type: String,
    @SerializedName("within")
    val within: String,
)