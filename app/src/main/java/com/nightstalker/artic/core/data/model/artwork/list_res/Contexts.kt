package com.nightstalker.artic.core.data.model.artwork.list_res


import com.google.gson.annotations.SerializedName

data class Contexts(
    @SerializedName("groupings")
    val groupings: List<String>
)