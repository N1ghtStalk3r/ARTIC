package com.nightstalker.artic.core.data.model.common


import com.google.gson.annotations.SerializedName

data class Info(
    @SerializedName("license_links")
    val licenseLinks: List<String>,
    @SerializedName("license_text")
    val licenseText: String,
    @SerializedName("version")
    val version: String
)