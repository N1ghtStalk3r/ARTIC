package com.nightstalker.artic.core.data.model.artwork


import com.google.gson.annotations.SerializedName

data class Color(
    @SerializedName("h")
    val h: Int,
    @SerializedName("l")
    val l: Int,
    @SerializedName("percentage")
    val percentage: Double,
    @SerializedName("population")
    val population: Int,
    @SerializedName("s")
    val s: Int
)