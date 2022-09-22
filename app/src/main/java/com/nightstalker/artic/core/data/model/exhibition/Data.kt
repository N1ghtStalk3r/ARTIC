package com.nightstalker.artic.core.data.model.exhibition


import com.google.gson.annotations.SerializedName
import com.nightstalker.artic.core.data.model.common.SuggestAutocompleteAll

data class Data(
    @SerializedName("aic_end_at")
    val aicEndAt: String,
    @SerializedName("aic_start_at")
    val aicStartAt: String,
    @SerializedName("alt_image_ids")
    val altImageIds: List<Any>,
    @SerializedName("api_link")
    val apiLink: String,
    @SerializedName("api_model")
    val apiModel: String,
    @SerializedName("artist_ids")
    val artistIds: List<Int>,
    @SerializedName("artwork_ids")
    val artworkIds: List<Int>,
    @SerializedName("artwork_titles")
    val artworkTitles: List<String>,
    @SerializedName("document_ids")
    val documentIds: List<Any>,
    @SerializedName("gallery_id")
    val galleryId: Int,
    @SerializedName("gallery_title")
    val galleryTitle: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image_id")
    val imageId: Any,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("is_featured")
    val isFeatured: Boolean,
    @SerializedName("short_description")
    val shortDescription: Any,
    @SerializedName("site_ids")
    val siteIds: List<Any>,
    @SerializedName("source_updated_at")
    val sourceUpdatedAt: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("suggest_autocomplete_all")
    val suggestAutocompleteAll: SuggestAutocompleteAll,
    @SerializedName("timestamp")
    val timestamp: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("web_url")
    val webUrl: String
)