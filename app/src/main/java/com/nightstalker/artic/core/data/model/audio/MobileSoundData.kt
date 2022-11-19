package com.nightstalker.artic.core.data.model.audio

import com.google.gson.annotations.SerializedName

/**
 *
 * @author Tamerlan Mamukhov
 * @created 2022-11-17
 */
data class MobileSoundData(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String? = "",
    @SerializedName("web_url")
    val webUrl: String? = "",
    @SerializedName("transcript")
    val transcript: String? = "",
)
