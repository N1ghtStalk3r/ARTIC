package com.nightstalker.artic.core.data.model.artwork.list_res


import com.google.gson.annotations.SerializedName
import com.nightstalker.artic.core.data.model.artwork.Data
import com.nightstalker.artic.core.data.model.common.Config
import com.nightstalker.artic.core.data.model.common.Info

data class ArtworksListResult(
    @SerializedName("config")
    val config: Config,
    @SerializedName("data")
    val data: List<Data>,
    @SerializedName("info")
    val info: Info,
    @SerializedName("pagination")
    val pagination: Pagination
)